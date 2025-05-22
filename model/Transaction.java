package model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public abstract class Transaction {

    private final UUID id = UUID.randomUUID();
    private final TransactionType type;
    private final BigDecimal amount;
    private TransactionStatus status = TransactionStatus.PENDING;
    private final Instant createdAt = Instant.now();
    private Instant processedAt;

    private String failureReason = "";

    protected Transaction(TransactionType type, BigDecimal amount) {
        this.type   = type;
        this.amount = amount;
    }

    public UUID getId() { 
        return id; 
    }

    public TransactionType getType() { 
        return type; 
    }

    public BigDecimal getAmount() { 
        return amount; 
    }

    public TransactionStatus getStatus() { 
        return status; 
    }
    
    public void markCompleted() {
        status = TransactionStatus.COMPLETED;
    }

    public void markFailed(String reason) {
        status = TransactionStatus.FAILED;
        failureReason = reason;
    }

    public String getFailureReason() { 
        return failureReason; 
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }
    
    public Instant getProcessedAt() {
        return this.processedAt;
    }

    public void setProcessedAt(Instant time) {
        this.processedAt = time;
    }

}
