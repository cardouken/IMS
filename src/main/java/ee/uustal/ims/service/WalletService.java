package ee.uustal.ims.service;

import java.math.BigDecimal;

public interface WalletService {

    /**
     * @param username      user identifier
     * @param txId          transaction identifier
     * @param balanceChange balance change
     */
    BalanceChangeResult updateBalance(String username, Integer txId, BigDecimal balanceChange);

    class BalanceChangeResult {

        private Integer txId;
        private BigDecimal balance;
        private BigDecimal balanceChange;
        private long balanceVersion;
        private Integer errorCode;

        public Integer getTxId() {
            return txId;
        }

        public BalanceChangeResult setTxId(Integer txId) {
            this.txId = txId;
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

        public Integer getErrorCode() {
            return errorCode;
        }

        public BalanceChangeResult setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
            return this;
        }
    }

}
