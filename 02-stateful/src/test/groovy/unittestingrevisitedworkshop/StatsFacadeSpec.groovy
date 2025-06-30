package unittestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop.Account
import pl.cezarysanecki.unittestingrevisitedworkshop.Stats
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsDownloader
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsFacade
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsRepository
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
}
