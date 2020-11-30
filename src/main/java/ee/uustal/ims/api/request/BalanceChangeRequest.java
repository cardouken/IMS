package ee.uustal.ims.api.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BalanceChangeRequest {

    @NotNull
    private String username;

    @NotNull
    private Integer transactionId;

    @NotNull
    private BigDecimal balanceChange;

    public String getUsername() {
        return username;
    }

    public BalanceChangeRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public BalanceChangeRequest setTransactionId(Integer transactionId) {
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
