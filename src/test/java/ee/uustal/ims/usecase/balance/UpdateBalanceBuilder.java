package ee.uustal.ims.usecase.balance;

import ee.uustal.ims.service.WalletService;

import java.math.BigDecimal;
import java.util.Random;

public class UpdateBalanceBuilder {

    private final WalletService walletService;
    private final String username;
    private long transactionId = Math.abs(new Random().nextLong());
    private BigDecimal balanceChange;

    public UpdateBalanceBuilder(WalletService walletService, String username) {
        this.walletService = walletService;
        this.username = username;
    }


    public UpdateBalanceBuilder withTransactionId(long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public UpdateBalanceBuilder updateBy(long amount) {
        this.balanceChange = BigDecimal.valueOf(amount);
        return this;
    }

    public WalletService.BalanceChangeResult build() {
        return walletService.updateBalance(
                username,
                transactionId,
                balanceChange
        );
    }
}
