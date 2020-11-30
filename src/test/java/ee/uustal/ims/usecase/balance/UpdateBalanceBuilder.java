package ee.uustal.ims.usecase.balance;

import ee.uustal.ims.service.WalletService;

import java.math.BigDecimal;
import java.util.Random;

public class UpdateBalanceBuilder {

    private final WalletService walletService;
    private final String username;
    private Integer transactionId = new Random().nextInt(6);
    private BigDecimal balanceChange;

    public UpdateBalanceBuilder(WalletService walletService, String username) {
        this.walletService = walletService;
        this.username = username;
    }


    public UpdateBalanceBuilder withTransactionId(Integer transactionId) {
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
