package ee.uustal.ims;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.repository.PlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

public class PlayerTest extends BaseTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void verify_scheduler_flush() {
        // given
        final String username = "mr coco jambo";
        final Player player = createPlayer().setUsername(username).build();
        for (int i = 0; i < 100; i++) {
            updateBalance(player).updateBy(5).build();
        }

        // when
        playerService.updateAll();

        // then
        final Optional<Player> playerFromDb = playerRepository.findByUserName(player.getUsername());
        Assert.assertTrue(playerFromDb.isPresent());
        Assert.assertEquals(username, playerFromDb.get().getUsername());
        Assert.assertEquals(BigDecimal.valueOf(500), playerFromDb.get().getBalance());
        Assert.assertEquals(101L, playerFromDb.get().getBalanceVersion());
    }
}
