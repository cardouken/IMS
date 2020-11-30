package ee.uustal.ims;

import ee.uustal.ims.service.PlayerServiceImpl;
import ee.uustal.ims.usecase.user.CreatePlayerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {ImsApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

    @Autowired
    protected PlayerServiceImpl playerServiceImpl;

    public CreatePlayerBuilder createPlayer() {
        return new CreatePlayerBuilder(playerServiceImpl);
    }

}
