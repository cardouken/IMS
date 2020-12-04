package ee.uustal.ims.adapter.api.request;

import javax.validation.constraints.NotNull;

public class PlayerBlacklistRequest {

    @NotNull
    private String username;

    public String getUsername() {
        return username;
    }

    public PlayerBlacklistRequest setUsername(String username) {
        this.username = username;
        return this;
    }
}
