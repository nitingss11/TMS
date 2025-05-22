package config;

import java.math.BigDecimal;

public final class TransactionConfig {
    private BigDecimal perTxnLimit;

    public TransactionConfig() {
        this.perTxnLimit = new BigDecimal("10000.00");
    }

    public void setPerTxnLimit(BigDecimal perTxnLimit) {
        this.perTxnLimit = perTxnLimit;
    }

    public BigDecimal getPerTxnLimit() {
        return perTxnLimit;
    }
}
