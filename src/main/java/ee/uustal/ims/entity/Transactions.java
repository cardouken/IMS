package ee.uustal.ims.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transactions {

    @Id
    private Integer id;

    private Integer playerId;

    private BigDecimal balanceChange;

    private LocalDateTime timestamp;

    private long balanceVersion;

    public Integer getId() {
        return id;
    }

    public Transactions setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Transactions setPlayerId(Integer playerId) {
        this.playerId = playerId;
        return this;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public Transactions setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Transactions setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public long getBalanceVersion() {
        return balanceVersion;
    }

    public Transactions setBalanceVersion(long balanceVersion) {
        this.balanceVersion = balanceVersion;
        return this;
    }
}
