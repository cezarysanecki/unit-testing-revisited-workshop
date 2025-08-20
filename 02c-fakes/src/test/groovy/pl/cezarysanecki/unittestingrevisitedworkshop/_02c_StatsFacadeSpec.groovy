package pl.cezarysanecki.unittestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.*
import spock.lang.Specification

class _02c_StatsFacadeSpec extends Specification {

    def idGenerator = new SimpleIdGenerator()

    def statsRepository = new InMemoryStatsRepository(() -> idGenerator.nextId())
    def importantStatsSystem = new InMemoryImportantStatsSystem()
    def additionalStatsSystem = new InMemoryAdditionalStatsSystem()
    def eventPublisher = new InMemoryEventPublisher<InconsistentDataEvent>()

    def statsDownload = new StatsDownloader(
            importantStatsSystem,
            additionalStatsSystem,
            eventPublisher
    )

    def sut = new StatsFacade(statsDownload, statsRepository)

    def "update stats for existing account using downloaded stats"() {

    }

    def "create account with downloaded stats when it does not exist"() {

    }

}
