package validation;

import config.TransactionConfig;
import exception.TransactionException;
import model.Transaction;

public final class PerTxnLimitValidator implements Validator<Transaction> {

    private final TransactionConfig cfg;
    public PerTxnLimitValidator(TransactionConfig cfg) { 
        this.cfg = cfg; 
    }

    @Override
    public void validate(Transaction txn) throws TransactionException {
        if (txn.getAmount().compareTo(cfg.getPerTxnLimit()) > 0) {
            throw new TransactionException("Amount exceeds per-transaction limit");
        }
    }
}
