package ee.uustal.ims.service;

import ee.uustal.ims.entity.Player;

public interface PlayerService {

    Player getOrCreatePlayer(String username);

    void cleanUp();

}
