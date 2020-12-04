package ee.uustal.ims.persistence.repository;

import ee.uustal.ims.persistence.entity.Blacklist;
import ee.uustal.ims.persistence.entity.Player;
import io.jsondb.JsonDBTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BlacklistRepository {

    private final JsonDBTemplate jsonDbTemplate;

    public BlacklistRepository(JsonDBTemplate jsonDbTemplate) {
        this.jsonDbTemplate = jsonDbTemplate;
    }

    public void update(Player player) {
        jsonDbTemplate.upsert(
                new Blacklist().setUsername(player.getUsername())
        );
    }

    public Blacklist findByUsername(String username) {
        return Optional.ofNullable(jsonDbTemplate.findById(username, Blacklist.class))
                .orElse(null);
    }

    public List<Blacklist> findAll() {
        return jsonDbTemplate.findAll(Blacklist.class);
    }

    public void remove(String username) {
        jsonDbTemplate.remove(findByUsername(username), Blacklist.class);
    }

    public void cleanup() {
        if (jsonDbTemplate.getCollection(Blacklist.class) != null) {
            final String query = "/.[username]";
            jsonDbTemplate.findAllAndRemove(query, Blacklist.class);
        }
    }
}
