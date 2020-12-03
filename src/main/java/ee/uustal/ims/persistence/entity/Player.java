package ee.uustal.ims.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String username;

    private long balanceVersion;

    private BigDecimal balance;

    public Integer getId() {
        return id;
    }

    public Player setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Player setUsername(String username) {
        this.username = username;
        return this;
    }

    public long getBalanceVersion() {
        return balanceVersion;
    }

    public Player setBalanceVersion(long balanceVersion) {
        this.balanceVersion = balanceVersion;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Player setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}