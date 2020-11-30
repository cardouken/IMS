package ee.uustal.ims.entity;

import java.math.BigDecimal;
import java.util.List;

public class Wallet {

    private final Player player;
    private BigDecimal balance;
    private long version;

    public Wallet(Player player, List<Transaction> transactions) {
        this.player = player;
        this.balance = player.getBalance();
        this.version = player.getBalanceVersion();
        apply(transactions);
    }


    private synchronized void apply(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            apply(transaction);
        }
    }

    public synchronized boolean apply(Transaction transaction) {
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
