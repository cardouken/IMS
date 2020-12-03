package ee.uustal.ims.config;

import io.jsondb.JsonDBTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Order(0)
public class ApplicationConfig {

    @Bean
    public JsonDBTemplate jsonDbTemplate() {
        return new JsonDBTemplate("/Users/cardo/repos/playtech-ims/src/main/resources", "ee.uustal.ims.persistence.entity");
    }
}
