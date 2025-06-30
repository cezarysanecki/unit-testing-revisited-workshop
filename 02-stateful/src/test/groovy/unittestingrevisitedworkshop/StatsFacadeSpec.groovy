package unittestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop.*
import spock.lang.Specification

class StatsFacadeSpec extends Specification {
    def "should return stats when account is found in repository"() {
        given:
            def accountId = UUID.randomUUID()
            // Teach the mock to always return specific stats
            StatsRepository statsRepository = Mock(StatsRepository)
            statsRepository.findByAccountId(accountId) >> Optional.of(new Stats(100, 1234, accountId, 300))

            StatsDownloader statsDownloader = Mock(StatsDownloader)
            StatsFacade statsFacade = new StatsFacade(statsDownloader, statsRepository)
            Account account = new Account(accountId, false)

        when:
            Stats stats = statsFacade.getStatsFor(account)

        then:
            0 * statsDownloader.downloadStatsFor(any())
    }

    def "should download stats from external systems and update repository when account not found in repository"() {
        given:
            def accountId = UUID.randomUUID()
            StatsRepository statsRepository = Mock(StatsRepository)
            statsRepository.findByAccountId(_ as UUID) >> Optional.empty()

            def externalStats = new ExternalStats(accountId, 0, 0)
            StatsDownloader statsDownloader = Mock(StatsDownloader)
            statsDownloader.downloadStatsFor(_ as UUID) >> externalStats

            StatsFacade statsFacade = new StatsFacade(statsDownloader, statsRepository)
            Account account = new Account(accountId, false)

        when:
            Stats stats = statsFacade.getStatsFor(account)

        then:
            // Tried downloading external stats
            1 * statsDownloader.downloadStatsFor(accountId)
            // Repository was updated
            1 * statsRepository.save(any())
    }
}
