package pl.cezarysanecki.unittestingrevisitedworkshop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsConfig {

    @Bean
    public StatsFacade statsFacade(
            StatsRepository statsRepository,
            ImportantStatsSystem importantStatsSystem,
            AdditionalStatsSystem additionalStatsSystem,
            EventPublisher<InconsistentDataEvent> eventPublisher
    ) {
        StatsDownloader statsDownloader = new StatsDownloader(
                importantStatsSystem,
                additionalStatsSystem,
                eventPublisher
        );
        return new StatsFacade(
                statsDownloader,
                statsRepository
        );
    }

}
