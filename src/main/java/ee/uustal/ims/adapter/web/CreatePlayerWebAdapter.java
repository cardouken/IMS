package ee.uustal.ims.adapter.web;

import ee.uustal.ims.adapter.api.request.PlayerGetOrCreateRequest;
import ee.uustal.ims.adapter.api.resource.PlayerResource;
import ee.uustal.ims.adapter.api.resource.PlayerResourceFactory;
import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.service.PlayerService;
import org.springframework.stereotype.Component;

@Component
public class CreatePlayerWebAdapter {

    private final PlayerService playerService;
    private final PlayerResourceFactory playerResourceFactory;

    public CreatePlayerWebAdapter(PlayerService playerService, PlayerResourceFactory playerResourceFactory) {
        this.playerService = playerService;
        this.playerResourceFactory = playerResourceFactory;
    }

    public PlayerResource getOrCreate(PlayerGetOrCreateRequest request) {
        final Player player = playerService.getOrCreate(request.getUsername());
        return playerResourceFactory.create(player);
    }
}
