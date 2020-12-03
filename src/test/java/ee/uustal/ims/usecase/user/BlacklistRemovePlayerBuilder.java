package ee.uustal.ims.usecase.user;

import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.repository.BlacklistRepository;

public class BlacklistRemovePlayerBuilder {

    private final BlacklistRepository blacklistRepository;
    private final Player player;

    public BlacklistRemovePlayerBuilder(BlacklistRepository blacklistRepository, Player player) {
        this.blacklistRepository = blacklistRepository;
        this.player = player;
    }

    public void build() {
        blacklistRepository.remove(player);
    }
}
