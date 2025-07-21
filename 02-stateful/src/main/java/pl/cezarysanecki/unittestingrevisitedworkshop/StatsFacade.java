package pl.cezarysanecki.unittestingrevisitedworkshop;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class StatsFacade {

    private final StatsDownloader statsDownloader;
    private final StatsRepository statsRepository;

    public StatsFacade(StatsDownloader statsDownloader, StatsRepository statsRepository) {
        this.statsDownloader = statsDownloader;
        this.statsRepository = statsRepository;
    }

    @Transactional
    public Stats getStatsFor(Account account) {
        Stats stats = statsRepository.findByAccountId(account.id()).orElseGet(() -> {
            Stats newStats = new Stats();
            newStats.accountId = account.id();
            return newStats;
        });

        if (stats.views == null || stats.likes == null) {
            ExternalStats externalStats = statsDownloader.downloadStatsFor(account.id());
            stats.views = externalStats.views();
            stats.likes = externalStats.likes();
            stats = statsRepository.save(stats);
        }

        return stats;
    }

}
