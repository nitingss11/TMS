package config;

import java.math.BigDecimal;

public final class TransactionConfig {
    /*private BigDecimal perTxnLimit;

    public TransactionConfig() {
        this.perTxnLimit = new BigDecimal("10000.00");
    }

    public void setPerTxnLimit(BigDecimal perTxnLimit) {
        this.perTxnLimit = perTxnLimit;
    }

    public BigDecimal getPerTxnLimit() {
        return perTxnLimit;
    }*/

    private BigDecimal typeAPerTxnLimit;
    private BigDecimal typeBPerTxnLimit;

    public TransactionConfig() {
        this.typeAPerTxnLimit = new BigDecimal("10000.00");
        this.typeBPerTxnLimit = new BigDecimal("20000.00");
    }

    public void setTypeAPerTxnLimit(BigDecimal typeAPerTxnLimit) {
        this.typeAPerTxnLimit = typeAPerTxnLimit;
    }

    public void setTypeBPerTxnLimit(BigDecimal typeBPerTxnLimit) {
        this.typeBPerTxnLimit = typeBPerTxnLimit;
    }

    public BigDecimal getTypeAPerTxnLimit() { 
        return typeAPerTxnLimit; 
    }
    public BigDecimal getTypeBPerTxnLimit() { 
        return typeBPerTxnLimit; 
    }

}
