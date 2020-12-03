package ee.uustal.ims.usecase.user;

import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.repository.BlacklistRepository;

public class BlacklistAddPlayerBuilder {

    private final BlacklistRepository blacklistRepository;
    private final Player player;

    public BlacklistAddPlayerBuilder(BlacklistRepository blacklistRepository, Player player) {
        this.blacklistRepository = blacklistRepository;
        this.player = player;
    }

    public void build() {
        blacklistRepository.update(player);
    }
}
