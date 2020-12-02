package ee.uustal.ims.service;

import java.math.BigDecimal;

public interface WalletService {

    /**
     * @param username username
     * @param transactionId transaction identifier
     * @param balanceChange balance change
     */
    BalanceChangeResult updateBalance(String username, Integer transactionId, BigDecimal balanceChange);

    void cleanUp();

    class BalanceChangeResult {

        private Integer transactionId;
        private BigDecimal balance;
        private BigDecimal balanceChange;
        private long balanceVersion;

        public Integer getTransactionId() {
            return transactionId;
        }

        public BalanceChangeResult setTransactionId(Integer transactionId) {
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
