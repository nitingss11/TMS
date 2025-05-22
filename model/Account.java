package model;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private final UUID id = UUID.randomUUID();
    private BigDecimal balance;
    private final ReentrantLock lock = new ReentrantLock();
    private final AccountType type;
    
    public Account(BigDecimal openingBalance, AccountType type) {
        this.balance = openingBalance;
        this.type = type;
    }

    public UUID getId() { 
        return id; 
    }

    public BigDecimal getBalance() { 
        return balance; 
    }

    public ReentrantLock getLock() { 
        return lock; 
    }

    public void debit(BigDecimal amt)  { 
        balance = balance.subtract(amt); 
    }

    public void credit(BigDecimal amt) { 
        balance = balance.add(amt); 
    }

    public AccountType getType() {
        return this.type;
    }
}
