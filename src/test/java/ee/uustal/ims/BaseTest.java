package ee.uustal.ims;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.service.PlayerService;
import ee.uustal.ims.service.WalletService;
import ee.uustal.ims.usecase.balance.UpdateBalanceBuilder;
import ee.uustal.ims.usecase.user.CreatePlayerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {ImsApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

    @Autowired
    protected PlayerService playerService;

    @Autowired
    protected WalletService walletService;

    @BeforeEach
    void setUp() {
        playerService.cleanUp();
        walletService.cleanUp();
    }

    public CreatePlayerBuilder createPlayer() {
        return new CreatePlayerBuilder(playerService);
    }

    public UpdateBalanceBuilder updateBalance(Player player) {
        return new UpdateBalanceBuilder(walletService, player.getUsername());
    }

}
