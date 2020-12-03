package ee.uustal.ims.persistence.entity;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "Blacklist", schemaVersion = "1.0")
public class Blacklist {

    @Id
    private Integer id;

    private String username;

    public Integer getId() {
        return id;
    }

    public Blacklist setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Blacklist setUsername(String username) {
        this.username = username;
        return this;
    }
}