package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class _02b_StatsFacadeSpec extends Specification {

    private static final def anAccount = new Account(UUID.randomUUID(), true)

    def statsRepository = Mock(StatsRepository)
    def importantStatsSystem = Mock(ImportantStatsSystem)
    def additionalStatsSystem = Mock(AdditionalStatsSystem)
    def eventPublisher = Mock(EventPublisher<InconsistentDataEvent>)

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
