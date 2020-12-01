package ee.uustal.ims.entity;

import ee.uustal.ims.exception.ApplicationLogicException;

import java.math.BigDecimal;
import java.util.List;

public class Wallet {

    private final Player player;
    private BigDecimal balance;
    private long version;

    public Wallet(Player player, List<TransactionEntity> transactions) {
        this.player = player;
        this.balance = player.getBalance();
        this.version = player.getBalanceVersion();
        apply(transactions);
    }


    private synchronized void apply(List<TransactionEntity> transactions) {
        for (TransactionEntity transaction : transactions) {
            apply(transaction);
        }
    }

    public synchronized boolean apply(TransactionEntity transaction) {
        if (this.balance.add(transaction.getBalanceChange()).signum() == -1) {
            throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.BALANCE_LESS_THAN_ZERO);
        }
        this.balance = balance.add(transaction.getBalanceChange());
        this.version++;
        return true;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public long getVersion() {
        return version;
    }
}
