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
import java.time.Instant;
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

        //acquire locks in ascending UUID order to avoid deadlock
        List<Account> ordered = from.getId().compareTo(to.getId()) < 0
        ? List.of(from, to)
        : List.of(to, from);

        ordered.get(0).getLock().lock();
        ordered.get(1).getLock().lock();

        //Validation
        try {
            runValidators(txn);
        } catch (TransactionException ex) {
            txn.setProcessedAt(Instant.now());
            txn.markFailed(ex.getMessage());
            ordered.get(1).getLock().unlock();
            ordered.get(0).getLock().unlock();
            return txn;
        }

        try {
            from.debit(amount);
            to.credit(amount);
            txn.setProcessedAt(Instant.now());
            txn.markCompleted();
        } finally {
            ordered.get(1).getLock().unlock();
            ordered.get(0).getLock().unlock();
        }
        return txn;
    }

    public CashTransaction cash(Account acc, TransactionType cashType, BigDecimal amount) {

        CashTransaction txn = new CashTransaction(acc, cashType, amount);

        acc.getLock().lock();

        try {
            runValidators(txn);
        } catch (TransactionException ex) {
            txn.setProcessedAt(Instant.now());
            txn.markFailed(ex.getMessage());
            acc.getLock().unlock();
            return txn;
        }

        try {
            if (cashType == TransactionType.CASH_CREDIT) {
                acc.credit(amount);
            } else {
                acc.debit(amount);
            }
            txn.setProcessedAt(Instant.now());
            txn.markCompleted();
        } finally {
            acc.getLock().unlock();
        }
        return txn;
    }
}
