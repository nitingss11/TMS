package service;

import model.Account;
import model.CashTransaction;
import model.TransactionType;
import model.TransferTransaction;
import java.math.BigDecimal;
import java.util.List;


public class TransactionService {

    public TransferTransaction transfer(Account from,
                                        Account to,
                                        BigDecimal amount) {

        TransferTransaction txn = new TransferTransaction(from, to, amount);

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

    public CashTransaction cash(Account acc,
                                TransactionType cashType,
                                BigDecimal amount) {

        CashTransaction txn = new CashTransaction(acc, cashType, amount);

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
