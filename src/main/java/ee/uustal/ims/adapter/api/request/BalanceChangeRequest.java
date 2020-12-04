package ee.uustal.ims.adapter.api.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BalanceChangeRequest {

    @NotNull
    private String username;

    @NotNull
    private long transactionId;

    @NotNull
    private BigDecimal balanceChange;

    public String getUsername() {
        return username;
    }

    public BalanceChangeRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public BalanceChangeRequest setTransactionId(long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public BalanceChangeRequest setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
        return this;
    }
}
