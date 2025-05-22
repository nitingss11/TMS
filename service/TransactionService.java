package service;

import model.Account;
import model.CashTransaction;
import model.Transaction;
import model.TransactionType;
import model.TransferTransaction;
import validation.BalanceValidator;
import validation.PerTxnLimitValidator;
import validation.Validator;
import config.TransactionConfig;
import exception.TransactionException;

import java.math.BigDecimal;
import java.util.List;


public class TransactionService {

    private final List<Validator<Transaction>> validators;

    public TransactionService(TransactionConfig cfg) {
        validators = List.of(new BalanceValidator(), new PerTxnLimitValidator(cfg));
    }

    private void runValidators(Transaction txn) throws TransactionException {
        for (Validator<Transaction> v : validators) v.validate(txn);
    }

    public TransferTransaction transfer(Account from, Account to, BigDecimal amount) {

        TransferTransaction txn = new TransferTransaction(from, to, amount);

        //Validation
        try {
            runValidators(txn);
        } catch (TransactionException ex) {
            txn.markFailed(ex.getMessage());
            return txn;
        }

        //acquire locks in ascending UUID order to avoid deadlock
        List<Account> ordered = from.getId().compareTo(to.getId()) < 0
                                ? List.of(from, to)
                                : List.of(to, from);

        ordered.get(0).getLock().lock();
        ordered.get(1).getLock().lock();

        try {
            from.debit(amount);
            to.credit(amount);
            txn.markCompleted();
        } finally {
            ordered.get(1).getLock().unlock();
            ordered.get(0).getLock().unlock();
        }
        return txn;
    }

    public CashTransaction cash(Account acc, TransactionType cashType, BigDecimal amount) {

        CashTransaction txn = new CashTransaction(acc, cashType, amount);

        try {
            runValidators(txn);
        } catch (TransactionException ex) {
            txn.markFailed(ex.getMessage());
            return txn;
        }

        acc.getLock().lock();
        try {
            if (cashType == TransactionType.CASH_CREDIT) {
                acc.credit(amount);
            } else {
                acc.debit(amount);
            }
            txn.markCompleted();
        } finally {
            acc.getLock().unlock();
        }
        return txn;
    }
}
