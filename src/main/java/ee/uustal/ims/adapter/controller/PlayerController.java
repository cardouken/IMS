package ee.uustal.ims.adapter.controller;

import ee.uustal.ims.adapter.api.request.PlayerBlacklistRequest;
import ee.uustal.ims.adapter.api.request.PlayerGetOrCreateRequest;
import ee.uustal.ims.adapter.api.request.PlayerUnblacklistRequest;
import ee.uustal.ims.adapter.api.resource.PlayerResource;
import ee.uustal.ims.adapter.web.CreatePlayerWebAdapter;
import ee.uustal.ims.service.PlayerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PlayerController {

    private final CreatePlayerWebAdapter createPlayerWebAdapter;
    private final PlayerService playerService;

    public PlayerController(CreatePlayerWebAdapter createPlayerWebAdapter, PlayerService playerService) {
        this.createPlayerWebAdapter = createPlayerWebAdapter;
        this.playerService = playerService;
    }

    @PostMapping(value = "/player/get-or-create")
    public PlayerResource getOrCreate(@Valid @RequestBody PlayerGetOrCreateRequest request) {
        return createPlayerWebAdapter.getOrCreate(request);
    }

    @PostMapping(value = "/player/blacklist")
    public void blacklist(@Valid @RequestBody PlayerBlacklistRequest request) {
        playerService.blacklist(request.getUsername());
    }

    @PostMapping(value = "/player/unblacklist")
    public void blacklist(@Valid @RequestBody PlayerUnblacklistRequest request) {
        playerService.unblacklist(request.getUsername());
    }
}
