package ee.uustal.ims.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private Integer id;

    private Integer playerId;

    private BigDecimal balanceChange;

    private LocalDateTime timestamp;

    private long balanceVersion;

    public Integer getId() {
        return id;
    }

    public Transaction setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Transaction setPlayerId(Integer playerId) {
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

    public long getBalanceVersion() {
        return balanceVersion;
    }

    public Transaction setBalanceVersion(long balanceVersion) {
        this.balanceVersion = balanceVersion;
        return this;
    }
}
