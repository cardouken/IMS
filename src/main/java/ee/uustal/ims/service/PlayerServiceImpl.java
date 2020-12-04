package ee.uustal.ims.service;

import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.repository.BlacklistRepository;
import ee.uustal.ims.persistence.repository.PlayerRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final BlacklistRepository blacklistRepository;
    private final Map<String, Player> storage = new ConcurrentHashMap<>();
    private final Logger logger = LogManager.getLogger(getClass());

    public PlayerServiceImpl(PlayerRepository playerRepository, BlacklistRepository blacklistRepository) {
        this.playerRepository = playerRepository;
        this.blacklistRepository = blacklistRepository;
    }

    @Override
    public synchronized Player getOrCreate(String username) {
        Player player = storage.get(username);
        if (player == null) {
            player = playerRepository.findByUserName(username)
                    .orElseGet(
                            () -> playerRepository.save(
                                    new Player()
                                            .setUsername(username)
                                            .setBalance(BigDecimal.ZERO)
                                            .setBalanceVersion(1L)
                            )
                    );
            storage.put(player.getUsername(), player);
        }
        return player;
    }

    @Override
    public void updateAll() {
        final Collection<Player> players = storage.values();
        for (Player player : players) {
            playerRepository.save(player);
        }
//        playerRepository.saveAll(players);
        logger.info(MessageFormat.format("Flushed memcache to database for a total of {0} player records", players.size()));
    }

    @Override
    public void blacklist(String username) {
        final Player player = getOrCreate(username);
        blacklistRepository.update(player);
    }

    @Override
    public void unblacklist(String username) {
        final Player player = getOrCreate(username);
        blacklistRepository.remove(player.getUsername());
    }

}