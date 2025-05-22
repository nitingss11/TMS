package validation;

import exception.TransactionException;
import model.*;

import java.math.BigDecimal;

public final class BalanceValidator implements Validator<Transaction> {

    @Override
    public void validate(Transaction txn) throws TransactionException {
        if (txn instanceof TransferTransaction t) {
            check(t.getFrom(), t.getAmount());
        } else if (txn instanceof CashTransaction c && c.getType() == TransactionType.CASH_DEBIT) {
            check(c.getAccount(), c.getAmount());
        }
    }

    private void check(Account src, BigDecimal amt) throws TransactionException {
        if (src.getBalance().compareTo(amt) < 0) {
            throw new TransactionException("Insufficient balance");
        }
    }
}
