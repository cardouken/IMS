package ee.uustal.ims.service;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class PeriodicScheduler {

    private final PlayerService playerService;

    public PeriodicScheduler(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Scheduled(fixedDelayString = "${ims.schedule.delay.memory-flush-interval}")
    public void flushMemoryToDatabase() {
        playerService.updateAll();
    }
}
