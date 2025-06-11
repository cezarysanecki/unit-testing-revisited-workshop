package pl.cezarysanecki.unittestingrevisitedworkshop;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatsFacade {

    private final StatsDownloader statsDownloader;
    private final StatsRepository statsRepository;

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

    @Transactional
    public Stats updateStatsAdHocFor(Account account, Long views, Long likes) {
        if (views == null || likes == null) {
            throw new IllegalArgumentException("Views and likes must not be null");
        }
        if (views < 0 || likes < 0) {
            throw new IllegalArgumentException("Views and likes must not be negative");
        }
        if (likes > views) {
            throw new IllegalArgumentException("Likes cannot be greater than views");
        }

        Stats stats = statsRepository.findByAccountId(account.id()).orElseGet(() -> {
            Stats newStats = new Stats();
            newStats.accountId = account.id();
            return newStats;
        });

        stats.views = views;
        stats.likes = likes;

        return statsRepository.save(stats);
    }

}
