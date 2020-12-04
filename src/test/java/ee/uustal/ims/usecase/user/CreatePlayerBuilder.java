package ee.uustal.ims.usecase.user;

import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.service.PlayerService;
import net.bytebuddy.utility.RandomString;

public class CreatePlayerBuilder {

    private final PlayerService playerService;
    private String username = RandomString.make(8);

    public CreatePlayerBuilder(PlayerService playerService) {
        this.playerService = playerService;
    }

    public CreatePlayerBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public Player build() {
        return playerService.getOrCreate(username);
    }
}
