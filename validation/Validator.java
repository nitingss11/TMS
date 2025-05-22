package validation;

import exception.TransactionException;
import model.Transaction;

@FunctionalInterface
public interface Validator<T extends Transaction> {
    void validate(T txn) throws TransactionException;
}
