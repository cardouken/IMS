package ee.uustal.ims.adapter.api.request;

import javax.validation.constraints.NotNull;

public class PlayerGetOrCreateRequest {

    @NotNull
    private String username;

    public String getUsername() {
        return username;
    }

    public PlayerGetOrCreateRequest setUsername(String username) {
        this.username = username;
        return this;
    }
}
