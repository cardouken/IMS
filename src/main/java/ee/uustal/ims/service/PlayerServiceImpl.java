package ee.uustal.ims.service;

import ee.uustal.ims.persistence.repository.PlayerRepository;
import ee.uustal.ims.persistence.entity.Player;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final Map<String, Player> storage = new ConcurrentHashMap<>();

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public synchronized Player getOrCreatePlayer(String username) {
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
    public synchronized void updateAll() {
        final Collection<Player> players = storage.values();
        playerRepository.saveAll(players);
    }

    @Override
    public void cleanUp() {
        playerRepository.deleteAll();
    }
}