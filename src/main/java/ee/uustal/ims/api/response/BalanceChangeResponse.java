package ee.uustal.ims.api.response;

import java.math.BigDecimal;

public class BalanceChangeResponse {

    private String transactionId;
    private int errorCode;
    private long balanceVersion;
    private BigDecimal balance;
    private BigDecimal balanceChange;

    public String getTransactionId() {
        return transactionId;
    }

    public BalanceChangeResponse setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public BalanceChangeResponse setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public long getBalanceVersion() {
        return balanceVersion;
    }

    public BalanceChangeResponse setBalanceVersion(long balanceVersion) {
        this.balanceVersion = balanceVersion;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BalanceChangeResponse setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public BalanceChangeResponse setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
        return this;
    }
}
