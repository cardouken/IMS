package ee.uustal.ims.service;

import ee.uustal.ims.persistence.entity.Player;

public interface PlayerService {

    Player getOrCreate(String username);

    void updateAll();

    void blacklist(String username);

    void unblacklist(String username);

    void cleanup();

}
