package ee.uustal.ims.service;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player getOrCreatePlayer(String username) {
        return playerRepository.findByUserName(username)
                .orElseGet(
                        () -> playerRepository.save(
                                new Player()
                                        .setUsername(username)
                                        .setBalance(BigDecimal.ZERO)
                                        .setBalanceVersion(1L)
                        )
                );
    }
}