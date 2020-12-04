package ee.uustal.ims;

import ee.uustal.ims.exception.ApplicationLogicException;
import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.service.WalletService;
import ee.uustal.ims.util.ExpectedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public class BalanceUpdateTest extends BaseTest {

    @Value("${ims.config.max-balance-increase}")
    private long maxBalanceIncrease;

    @Test
    public void update_player_balance() {
        // given
        final Player player = createPlayer().setUsername("mr_user").build();
        Assertions.assertEquals(BigDecimal.ZERO, player.getBalance());

        // when
        final int txId = 69420;
        final WalletService.BalanceChangeResult result = updateBalance(player).withTransactionId(txId).updateBy(2000).build();

        // then
        Assertions.assertEquals(BigDecimal.valueOf(2000), result.getBalanceChange());
        Assertions.assertEquals(BigDecimal.valueOf(2000), result.getBalance());
        Assertions.assertEquals(txId, result.getTransactionId());
        Assertions.assertEquals(2L, result.getBalanceVersion());
    }

    @Test
    public void set_balance_over_max_limit() {
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
    public void set_balance_to_negative_amount() {
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
    public void duplicate_transaction() {
        // given
        final Player player = createPlayer().build();
        final int txId = 42069;
        updateBalance(player).withTransactionId(txId).updateBy(69).build();

        // when
        final WalletService.BalanceChangeResult secondUpdate = updateBalance(player).withTransactionId(txId).updateBy(420).build();

        // then
        Assertions.assertEquals(BigDecimal.valueOf(69), secondUpdate.getBalanceChange());
        Assertions.assertEquals(BigDecimal.valueOf(69), secondUpdate.getBalance());
        Assertions.assertEquals(txId, secondUpdate.getTransactionId());
        Assertions.assertEquals(2L, secondUpdate.getBalanceVersion());
    }

    @Test
    public void check_sum() {
        // given
        final Player player = createPlayer().build();
        updateBalance(player).updateBy(5).build();
        updateBalance(player).updateBy(5).build();

        // when
        final WalletService.BalanceChangeResult result = updateBalance(player).updateBy(5).build();

        // then
        Assertions.assertEquals(BigDecimal.valueOf(5), result.getBalanceChange());
        Assertions.assertEquals(BigDecimal.valueOf(15), result.getBalance());
    }

    @Test
    public void only_check_for_last_1000_transactions() {
        // given
        final Player player = createPlayer().build();
        for (int i = 0; i < 1500; i++) {
            updateBalance(player).withTransactionId(i).updateBy(1).build();
        }

        // when
        int txId = 404;
        final WalletService.BalanceChangeResult result = updateBalance(player).withTransactionId(txId).updateBy(100).build();

        // then
        Assertions.assertEquals(BigDecimal.valueOf(100), result.getBalanceChange());
        Assertions.assertEquals(BigDecimal.valueOf(1600), result.getBalance());
        Assertions.assertEquals(txId, result.getTransactionId());
        Assertions.assertEquals(1502L, result.getBalanceVersion());
    }

    @Test
    public void try_changing_balance_for_blacklisted_player() {
        // given
        final Player player = createPlayer().build();
        blacklist(player).build();

        // when -> then
        ExpectedException.expect(
                () -> updateBalance(player).updateBy(404).build(),
                ApplicationLogicException.class,
                "error.player-blacklisted"
        );
    }
}
