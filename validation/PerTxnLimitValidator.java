package validation;

import java.math.BigDecimal;

import config.TransactionConfig;
import exception.TransactionException;
import model.Account;
import model.AccountType;
import model.CashTransaction;
import model.Transaction;
import model.TransactionType;
import model.TransferTransaction;

public final class PerTxnLimitValidator implements Validator<Transaction> {

    private final TransactionConfig cfg;
    public PerTxnLimitValidator(TransactionConfig cfg) { 
        this.cfg = cfg; 
    }

    @Override
    public void validate(Transaction txn) throws TransactionException {
        
        Account source = null;

        if (txn instanceof TransferTransaction t) {
            source = t.getFrom();
        } else if (txn instanceof CashTransaction c && c.getType() == TransactionType.CASH_DEBIT) {
            //Not validating Cash CREDIT
            source = c.getAccount();
        }

        if (source == null) {
            return;
        }

        BigDecimal limit = (source.getType() == AccountType.TypeA)
                           ? cfg.getTypeAPerTxnLimit()
                           : cfg.getTypeBPerTxnLimit();

        if (txn.getAmount().compareTo(limit) > 0) {
            throw new TransactionException(
                "Amount exceeds per-transaction limit for " + source.getType() + " account"
            );
        }
    }
}
