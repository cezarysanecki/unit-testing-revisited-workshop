package unittestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop.*
import spock.lang.Specification

class StatsFacadeSpec extends Specification {
    def "should return stats when account is found in repository"() {
        given:
        def accountId = UUID.randomUUID()
        def views = 100
        def statsId = 1234
        def likes = 300

        // Teach the mock to always return specific stats
        StatsRepository statsRepository = Mock(StatsRepository)
        statsRepository.findByAccountId(_ as UUID) >> Optional.of(new Stats(views, statsId, accountId, likes))

        StatsDownloader statsDownloader = Mock(StatsDownloader)
        StatsFacade statsFacade = new StatsFacade(statsDownloader, statsRepository)
        Account account = new Account(accountId, false)

        when:
        Stats stats = statsFacade.getStatsFor(account)

        then:
        0 * statsDownloader.downloadStatsFor(any())
        // Verify stats
        stats.accountId == accountId
        stats.id == statsId
        stats.views == views
        stats.likes == likes
    }

    def "should download stats from external systems and update repository when account not found in repository"() {
        given:
        def accountId = UUID.randomUUID()
        def views = 100
        def statsId = 1234
        def likes = 300

        StatsRepository statsRepository = Mock(StatsRepository)
        statsRepository.findByAccountId(_ as UUID) >> Optional.empty()

        def externalStats = new ExternalStats(accountId, views, likes)
        StatsDownloader statsDownloader = Mock(StatsDownloader)

        StatsFacade statsFacade = new StatsFacade(statsDownloader, statsRepository)
        Account account = new Account(accountId, false)

        when:
        Stats stats = statsFacade.getStatsFor(account)

        then:
        // Tried downloading external stats
        1 * statsDownloader.downloadStatsFor(accountId) >> externalStats
        // Repository was updated
        1 * statsRepository.save(_ as Stats)
    }
}
