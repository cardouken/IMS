package ee.uustal.ims;

import ee.uustal.ims.persistence.entity.Blacklist;
import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.repository.PlayerRepository;
import ee.uustal.ims.util.ExpectedException;
import io.jsondb.InvalidJsonDbApiUsageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
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
        Assertions.assertTrue(playerFromDb.isPresent());
        Assertions.assertEquals(username, playerFromDb.get().getUsername());
        Assertions.assertEquals(BigDecimal.valueOf(500), playerFromDb.get().getBalance());
        Assertions.assertEquals(101L, playerFromDb.get().getBalanceVersion());
    }

    @Test
    public void add_player_to_blacklist() {
        // given
        final Player player = createPlayer().build();

        // when
        blacklist(player).build();

        // then
        final Blacklist blacklist = blacklistRepository.findByUsername(player.getUsername());
        Assertions.assertEquals(player.getUsername(), blacklist.getUsername());
    }

    @Test
    public void remove_player_from_blacklist() {
        // given
        final Player player = createPlayer().build();

        // when
        blacklist(player).build();
        unBlacklist(player).build();

        // then
        Assertions.assertNull(blacklistRepository.findByUsername(player.getUsername()));
    }

    @Test
    public void try_blacklisting_player_multiple_times() {
        // given
        final Player player = createPlayer().build();
        blacklist(player).build();

        // when
        blacklist(player).build();

        // then
        final List<Blacklist> allBlacklistedPlayers = blacklistRepository.findAll();
        Assertions.assertEquals(1, allBlacklistedPlayers.size());
        Assertions.assertEquals(player.getUsername(), allBlacklistedPlayers.get(0).getUsername());
    }

    @Test
    public void try_unblacklisting_non_blacklisted_player() {
        // given
        final Player player = createPlayer().build();

        // when -> then
        ExpectedException.expect(
                () -> unBlacklist(player).build(),
                InvalidJsonDbApiUsageException.class
        );
    }
}
