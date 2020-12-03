package ee.uustal.ims.persistence.repository;

import ee.uustal.ims.persistence.entity.Blacklist;
import ee.uustal.ims.persistence.entity.Player;
import io.jsondb.JsonDBTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Repository
public class BlacklistRepository {

    private final JsonDBTemplate jsonDbTemplate;

    public BlacklistRepository(JsonDBTemplate jsonDbTemplate) {
        this.jsonDbTemplate = jsonDbTemplate;
    }

    public void update(Player player) {
        if (jsonDbTemplate.getCollection(Blacklist.class) == null) {
            jsonDbTemplate.createCollection(Blacklist.class);
        }
        jsonDbTemplate.upsert(
                new Blacklist()
                        .setId(player.getId())
                        .setUsername(player.getUsername()),
                "Blacklist"
        );
    }

    public Blacklist findById(Integer playerId) {
        if (jsonDbTemplate.getCollection(Blacklist.class) == null) {
            jsonDbTemplate.createCollection(Blacklist.class);
        }
        return Optional.ofNullable(jsonDbTemplate.findById(playerId, Blacklist.class))
                .orElse(null);
    }

    public List<Blacklist> findAll() {
        return jsonDbTemplate.findAll(Blacklist.class);
    }

    public void remove(Player player) {
        jsonDbTemplate.remove(findById(player.getId()), Blacklist.class);
    }

    public void cleanup() {
        if (jsonDbTemplate.getCollection(Blacklist.class) != null) {
            jsonDbTemplate.dropCollection(Blacklist.class);
        }
    }
}
