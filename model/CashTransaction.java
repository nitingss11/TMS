package model;

import java.math.BigDecimal;

public class CashTransaction extends Transaction {
    private final Account account;

    public CashTransaction(Account account, TransactionType cashType, BigDecimal amount) {
        super(cashType, amount);
        this.account = account;
    }
    public Account getAccount() { 
        return account; 
    }
}
