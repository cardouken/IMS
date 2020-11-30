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
    public void increase_balance_over_predefined_limit() {
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
    public void balance_negative_after_change() {
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
    public void balance_change_already_exists() {
        // given
        final Player player = createPlayer().build();
        final int txId = 69420;
        updateBalance(player).withTransactionId(txId).updateBy(69).build();

        // when
        updateBalance(player).withTransactionId(txId).updateBy(420).build();

        // then
    }
}
