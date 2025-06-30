import pl.cezarysanecki.unittestingrevisitedworkshop.Account
import pl.cezarysanecki.unittestingrevisitedworkshop.EventPublisher
import pl.cezarysanecki.unittestingrevisitedworkshop.FirstExternalSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.SecondExternalSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.Stats
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsDownloader
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsFacade
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsRepository
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryEventPublisher
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryFirstExternalSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemorySecondExternalSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryStatsRepository
import spock.lang.Specification

class UpdateStatisticStatsFacadeSpec extends Specification {

    FirstExternalSystem firstExternalSystem = new InMemoryFirstExternalSystem()
    SecondExternalSystem secondExternalSystem = new InMemorySecondExternalSystem()
    EventPublisher eventPublisher = new InMemoryEventPublisher()

    StatsRepository statsRepository = new InMemoryStatsRepository(() -> System.currentTimeMillis())
    StatsDownloader statsDownloader = new StatsDownloader(firstExternalSystem, secondExternalSystem, eventPublisher)

    def "should update statistics based on new views and likes"() {
        given:
            def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174011")
            def account = new Account(accountId, true)
            def statsFacade = new StatsFacade(statsDownloader, statsRepository)
            statsRepository.save(new Stats(12L, accountId, views, likes ))

        when:
            def stats = statsFacade.updateStatsAdHocFor(account, views * 2, likes * 2)

        then:
            stats.accountId == accountId
            stats.views == 2 * views
            stats.likes == 2 *likes

        where:
            likes | views
            0L     | 0L
            100L   | 1000L
    }

    def "should update new created statistic when is not existing"() {
        given:
            def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174012")
            def account = new Account(accountId, true)
            def statsFacade = new StatsFacade(statsDownloader, statsRepository)

        when:
            def stats = statsFacade.updateStatsAdHocFor(account, views * 2, likes * 2)

        then:
            stats.accountId == accountId
            stats.views == 2 * views
            stats.likes == 2 *likes

        where:
            likes | views
            0     | 0
            100   | 1000
    }

    def "should throw exception when updated data is incorrect"() {
        given:
            def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174013")
            def account = new Account(accountId, true)
            def statsFacade = new StatsFacade(statsDownloader, statsRepository)

        when:
            statsFacade.updateStatsAdHocFor(account, views, likes)

        then:
            thrown(IllegalArgumentException)

        where:
            likes | views
            1000  | 100
            -1000 | -2000
            -1000 | 100
            100   | -100
            -1000 | -1000
            null  | null
    }
}
