package ee.uustal.ims.service;

import java.math.BigDecimal;

public interface WalletService {

    /**
     * @param username      username
     * @param transactionId transaction identifier
     * @param balanceChange balance change
     */
    BalanceChangeResult updateBalance(String username, long transactionId, BigDecimal balanceChange);

    class BalanceChangeResult {

        private long transactionId;
        private BigDecimal balance;
        private BigDecimal balanceChange;
        private long balanceVersion;

        public long getTransactionId() {
            return transactionId;
        }

        public BalanceChangeResult setTransactionId(long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public BalanceChangeResult setBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public BigDecimal getBalanceChange() {
            return balanceChange;
        }

        public BalanceChangeResult setBalanceChange(BigDecimal balanceChange) {
            this.balanceChange = balanceChange;
            return this;
        }

        public long getBalanceVersion() {
            return balanceVersion;
        }

        public BalanceChangeResult setBalanceVersion(long balanceVersion) {
            this.balanceVersion = balanceVersion;
            return this;
        }
    }

}
