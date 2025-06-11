package pl.cezarysanecki.unittestingrevisitedworkshop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsConfig {

    @Bean
    public StatsFacade statsFacade(
            StatsRepository statsRepository,
            FirstExternalSystem firstExternalSystem,
            SecondExternalSystem secondExternalSystem,
            EventPublisher eventPublisher
    ) {
        StatsDownloader statsDownloader = new StatsDownloader(
                firstExternalSystem,
                secondExternalSystem,
                eventPublisher
        );
        return new StatsFacade(
                statsDownloader,
                statsRepository
        );
    }

}
