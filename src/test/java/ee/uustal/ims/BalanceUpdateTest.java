package ee.uustal.ims;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.exception.ApplicationLogicException;
import ee.uustal.ims.service.WalletService;
import ee.uustal.ims.util.ExpectedException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public class BalanceUpdateTest extends BaseTest {

    @Value("${ims.max-balance-increase}")
    private long maxBalanceIncrease;

    @Test
    public void update_player_balance() {
        // given
        final Player player = createPlayer().setUsername("mr_user").build();
        Assert.assertEquals(BigDecimal.ZERO, player.getBalance());

        // when
        final int txId = 69420;
        final WalletService.BalanceChangeResult result = updateBalance(player).withTransactionId(txId).updateBy(2000).build();

        // then
        Assert.assertEquals(BigDecimal.valueOf(2000), result.getBalanceChange());
        Assert.assertEquals(BigDecimal.valueOf(2000), result.getBalance());
        Assert.assertEquals(txId, result.getTxId());
        Assert.assertEquals(2L, result.getBalanceVersion());
    }

    @Test
    public void try_increasing_balance_over_max_limit() {
        // given
        final Player player = createPlayer().build();

        // when -> then
        ExpectedException.expect(
                () -> updateBalance(player).updateBy(maxBalanceIncrease + 1).build(),
                ApplicationLogicException.class,
                "error.balance-change-exceeds-max-limit"
        );
    }

    @Test
    public void try_setting_balance_to_negative() {
        // given
        final Player player = createPlayer().build();

        // when -> then
        ExpectedException.expect(
                () -> updateBalance(player).updateBy(-2000).build(),
                ApplicationLogicException.class,
                "error.balance-less-than-zero"
        );
    }

    @Test
    public void transaction_already_exists() {
        // given
        final Player player = createPlayer().build();
        final int txId = 42069;
        final WalletService.BalanceChangeResult firstUpdate = updateBalance(player).withTransactionId(txId).updateBy(69).build();

        // when
        updateBalance(player).withTransactionId(txId).updateBy(420).build();

        // then
        Assert.assertEquals(BigDecimal.valueOf(69), firstUpdate.getBalanceChange());
        Assert.assertEquals(BigDecimal.valueOf(69), firstUpdate.getBalance());
        Assert.assertEquals(txId, firstUpdate.getTxId());
        Assert.assertEquals(2L, firstUpdate.getBalanceVersion());
    }

    @Test
    public void check_sum() {
        // given
        final Player player = createPlayer().build();
        updateBalance(player).withTransactionId(1).updateBy(5).build();
        updateBalance(player).withTransactionId(2).updateBy(5).build();

        // when
        final WalletService.BalanceChangeResult result = updateBalance(player).withTransactionId(3).updateBy(5).build();

        // then
        Assert.assertEquals(BigDecimal.valueOf(5), result.getBalanceChange());
        Assert.assertEquals(BigDecimal.valueOf(15), result.getBalance());
    }

    @Test
    public void only_check_for_last_1000_transactions() {
        // given
        final Player player = createPlayer().build();
        for (int i = 1; i <= 1500; i++) {
            updateBalance(player).withTransactionId(i).updateBy(5).build();
        }

        // when
        final WalletService.BalanceChangeResult result = updateBalance(player).withTransactionId(400).updateBy(100).build();

        // then
        Assert.assertEquals(BigDecimal.valueOf(100), result.getBalanceChange());
        Assert.assertEquals(BigDecimal.valueOf(7600), result.getBalance());
        Assert.assertEquals(400, result.getTxId());
//        Assert.assertEquals(2L, result.getBalanceVersion());
    }
}
