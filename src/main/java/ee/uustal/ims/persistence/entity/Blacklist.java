package ee.uustal.ims.persistence.entity;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "blacklist", schemaVersion = "1.0")
public class Blacklist {

    @Id
    private String username;

    public String getUsername() {
        return username;
    }

    public Blacklist setUsername(String username) {
        this.username = username;
        return this;
    }
}