package pl.cezarysanecki.unittestingrevisitedworkshop;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatsFacade {
    // Collaborator
    private final StatsDownloader statsDownloader;
    private final StatsRepository statsRepository;

    @Transactional
    public Stats getStatsFor(Account account) {
        Stats stats = statsRepository.findByAccountId(account.id()).orElseGet(() -> {
            return new Stats(account.id());
        });

        // If stats are not present, download them from the external system
        if (stats.views == null || stats.likes == null) {
            ExternalStats externalStats = statsDownloader.downloadStatsFor(account.id());
            stats.views = externalStats.views();
            stats.likes = externalStats.likes();
            stats = statsRepository.save(stats);
        }

        return stats;
    }
}
