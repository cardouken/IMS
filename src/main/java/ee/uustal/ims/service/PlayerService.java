package ee.uustal.ims.service;

import ee.uustal.ims.persistence.entity.Player;

public interface PlayerService {

    Player getOrCreatePlayer(String username);

    void updateAll();

    void cleanUp();
}
