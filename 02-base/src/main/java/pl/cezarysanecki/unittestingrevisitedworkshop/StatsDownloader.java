package pl.cezarysanecki.unittestingrevisitedworkshop;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class StatsDownloader {

    private final ImportantStatsSystem importantStatsSystem;
    private final AdditionalStatsSystem additionalStatsSystem;
    private final EventPublisher<InconsistentDataEvent> eventPublisher;

    public StatsDownloader(
            ImportantStatsSystem importantStatsSystem,
            AdditionalStatsSystem additionalStatsSystem,
            EventPublisher<InconsistentDataEvent> eventPublisher
    ) {
        this.importantStatsSystem = importantStatsSystem;
        this.additionalStatsSystem = additionalStatsSystem;
        this.eventPublisher = eventPublisher;
    }

    public ExternalStats downloadStatsFor(UUID accountId) {
        var importantStats = importantStatsSystem.downloadStatsFor(accountId);
        var additionalStats = additionalStatsSystem.downloadStatsFor(accountId);

        if (!Objects.equals(importantStats.views(), additionalStats.views())
                || !Objects.equals(importantStats.likes(), additionalStats.likes())) {
            eventPublisher.publish(new InconsistentDataEvent(accountId));
        }

        return new ExternalStats(
                accountId,
                importantStats.views(),
                importantStats.likes()
        );
    }

}
