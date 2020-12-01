package ee.uustal.ims.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class TransactionEntity {

    @Id
    private Integer id;

    private Integer playerId;

    private BigDecimal balanceChange;

    private LocalDateTime timestamp;

    private long balanceVersion;

    public Integer getId() {
        return id;
    }

    public TransactionEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public TransactionEntity setPlayerId(Integer playerId) {
        this.playerId = playerId;
        return this;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public TransactionEntity setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionEntity setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public long getBalanceVersion() {
        return balanceVersion;
    }

    public TransactionEntity setBalanceVersion(long balanceVersion) {
        this.balanceVersion = balanceVersion;
        return this;
    }
}
