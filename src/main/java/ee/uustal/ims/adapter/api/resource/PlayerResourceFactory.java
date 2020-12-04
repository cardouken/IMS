package ee.uustal.ims.adapter.api.resource;

import ee.uustal.ims.persistence.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerResourceFactory {

    public PlayerResource create(Player player) {
        return new PlayerResource()
                .setUsername(player.getUsername())
                .setBalance(player.getBalance())
                .setBalanceVersion(player.getBalanceVersion());
    }
}
