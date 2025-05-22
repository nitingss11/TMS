package model;

import java.math.BigDecimal;

public class TransferTransaction extends Transaction {
    private final Account from;
    private final Account to;

    public TransferTransaction(Account from, Account to, BigDecimal amount) {
        super(TransactionType.ACCOUNT_TRANSFER_DEBIT, amount); // track debit, and credit gets recorded
        this.from = from;
        this.to   = to;
    }
    
    public Account getFrom() { 
        return from; 
    }

    public Account getTo() { 
        return to; 
    }
}
