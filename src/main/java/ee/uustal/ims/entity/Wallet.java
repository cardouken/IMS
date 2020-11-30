package ee.uustal.ims.entity;

import ee.uustal.ims.exception.ApplicationLogicException;

import java.math.BigDecimal;
import java.util.List;

public class Wallet {

    private final Player player;
    private BigDecimal balance;
    private long version;

    public Wallet(Player player, List<Transactions> transactions) {
        this.player = player;
        this.balance = player.getBalance();
        this.version = player.getBalanceVersion();
        apply(transactions);
    }


    private synchronized void apply(List<Transactions> transactions) {
        for (Transactions transaction : transactions) {
            apply(transaction);
        }
    }

    public synchronized boolean apply(Transactions transaction) {
        if (this.balance.add(transaction.getBalanceChange()).signum() == -1) {
            throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.BALANCE_LESS_THAN_ZERO);
        }
        this.balance = balance.add(transaction.getBalanceChange());
        this.version++;
        return false;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public long getVersion() {
        return version;
    }
}
