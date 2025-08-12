package pl.cezarysanecki.unitestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop.Account
import pl.cezarysanecki.unittestingrevisitedworkshop.ExternalStats
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsDownloader
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsFacade
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryAdditionalStatsSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryEventPublisher
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryImportantStatsSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryStatsRepository
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.SimpleIdGenerator
import spock.lang.Specification

class _02d_StatsFacadeSpec extends Specification {

    def importantStatsSystem = new InMemoryImportantStatsSystem()
    def additionalStatsSystem = new InMemoryAdditionalStatsSystem()
    def idGenerator = new SimpleIdGenerator()
    def statsRepository = new InMemoryStatsRepository(() -> idGenerator.nextId())
    def eventPublisher = new InMemoryEventPublisher()

    def statsDownloader = new StatsDownloader(
            importantStatsSystem,
            additionalStatsSystem,
            eventPublisher
    )

    def statsFacade = new StatsFacade(
            statsDownloader,
            statsRepository
    )

    private static final ACCOUNT = new Account(UUID.randomUUID(), true)

    def "should fail when more likes than views"() {
        when:
        statsFacade.updateStatsAdHocFor(ACCOUNT, 100, 120)

        then:
        thrown(IllegalArgumentException)

    }

}
