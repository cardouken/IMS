package ee.uustal.ims.entity;

import ee.uustal.ims.exception.ApplicationLogicException;

import java.math.BigDecimal;
import java.util.List;

public class Wallet {

    private BigDecimal balance;
    private long version;

    public Wallet(Player player, List<Transaction> transactions) {
        this.balance = player.getBalance();
        this.version = player.getBalanceVersion();
        apply(transactions);
    }

    private synchronized void apply(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            apply(transaction);
        }
    }

    public synchronized void apply(Transaction transaction) {
        if (resultingBalanceNegative(transaction.getBalanceChange())) {
            throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.BALANCE_LESS_THAN_ZERO);
        }
        this.balance = balance.add(transaction.getBalanceChange());
        this.version++;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public long getVersion() {
        return version;
    }

    private boolean resultingBalanceNegative(BigDecimal balanceChange) {
        return this.balance.add(balanceChange).signum() == -1;
    }

    public boolean balanceNegative() {
        return this.balance.signum() == -1;
    }
}
