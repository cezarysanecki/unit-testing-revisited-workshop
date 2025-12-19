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
        ExternalStats externalStats = statsDownloader.downloadStatsFor(account.id());

        Stats stats = statsRepository.findByAccountId(account.id()).orElseGet(() -> {
            Stats newStats = new Stats();
            newStats.accountId = account.id();
            return newStats;
        });

        stats.views = externalStats.views();
        stats.likes = externalStats.likes();

        stats = statsRepository.save(stats);

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
