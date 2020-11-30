package ee.uustal.ims;

import ee.uustal.ims.entity.Player;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class BalanceUpdateTest extends BaseTest {

    @Test
    public void update_balance() {
        // given
        final Player player = createPlayer().setUsername("mr_user").build();

        // then
        Assert.assertEquals("mr_user", player.getUsername());

    }
}
