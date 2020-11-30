package ee.uustal.ims.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private String id;

    private String playerId;

    private BigDecimal balanceChange;

    private LocalDateTime timestamp;

    private long balanceVersion;

    public String getId() {
        return id;
    }

    public Transaction setId(String id) {
        this.id = id;
        return this;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Transaction setPlayerId(String playerId) {
        this.playerId = playerId;
        return this;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public Transaction setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Transaction setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

}
