package ee.uustal.ims.config;

import ee.uustal.ims.persistence.entity.Blacklist;
import io.jsondb.JsonDBTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ApplicationConfig {

    @Bean
    public JsonDBTemplate jsonDbTemplate() {
        final JsonDBTemplate jsonDBTemplate = new JsonDBTemplate("src/main/resources/db", "ee.uustal.ims.persistence.entity");
        if (jsonDBTemplate.getCollection(Blacklist.class) == null) {
            jsonDBTemplate.createCollection(Blacklist.class);
        }
        return jsonDBTemplate;
    }
}
