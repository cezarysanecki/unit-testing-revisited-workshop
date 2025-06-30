package pl.cezarysanecki.unittestingrevisitedworkshop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

// Download data from two external systems;
// if they are inconsistent, send an inconsistent data event;
// return data from the first system
@Component
@RequiredArgsConstructor
public class StatsDownloader {

    private final FirstExternalSystem firstExternalSystem;
    private final SecondExternalSystem secondExternalSystem;
    private final EventPublisher eventPublisher;

    public ExternalStats downloadStatsFor(UUID accountId) {
        var firstStats = firstExternalSystem.downloadStatsFor(accountId);
        var secondStats = secondExternalSystem.downloadStatsFor(accountId);

        if (!Objects.equals(firstStats.views(), secondStats.views())
                || !Objects.equals(firstStats.likes(), secondStats.likes())) {
            eventPublisher.publish(new InconsistentData(accountId));
        }

        return new ExternalStats(
                accountId,
                firstStats.views(),
                firstStats.likes()
        );
    }

}
