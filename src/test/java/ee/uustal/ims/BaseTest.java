package ee.uustal.ims;

import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.repository.BlacklistRepository;
import ee.uustal.ims.persistence.repository.PlayerRepository;
import ee.uustal.ims.service.PlayerService;
import ee.uustal.ims.service.WalletService;
import ee.uustal.ims.usecase.balance.UpdateBalanceBuilder;
import ee.uustal.ims.usecase.user.BlacklistAddPlayerBuilder;
import ee.uustal.ims.usecase.user.BlacklistRemovePlayerBuilder;
import ee.uustal.ims.usecase.user.CreatePlayerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {ImsApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(BaseTest.TEST_PROFILE)
public abstract class BaseTest {

    static final String TEST_PROFILE = "test";

    @Autowired
    protected PlayerService playerService;

    @Autowired
    protected WalletService walletService;

    @Autowired
    protected BlacklistRepository blacklistRepository;

    @Autowired
    protected PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        blacklistRepository.cleanup();
    }

    public CreatePlayerBuilder createPlayer() {
        return new CreatePlayerBuilder(playerService);
    }

    public UpdateBalanceBuilder updateBalance(Player player) {
        return new UpdateBalanceBuilder(walletService, player.getUsername());
    }

    public BlacklistAddPlayerBuilder blacklist(Player player) {
        return new BlacklistAddPlayerBuilder(blacklistRepository, player);
    }

    public BlacklistRemovePlayerBuilder unBlacklist(Player player) {
        return new BlacklistRemovePlayerBuilder(blacklistRepository, player);
    }

}
