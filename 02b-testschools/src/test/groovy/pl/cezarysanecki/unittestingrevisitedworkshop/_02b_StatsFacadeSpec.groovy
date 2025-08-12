package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class _02b_StatsFacadeSpec extends Specification {

    def importantStatsSystem = Mock(ImportantStatsSystem)
    def additionalStatsSystem = Mock(AdditionalStatsSystem)
    def eventPublisher = Mock(EventPublisher)

    StatsRepository repository = Mock(StatsRepository)
    StatsDownloader downloader = new StatsDownloader(importantStatsSystem, additionalStatsSystem, eventPublisher)
    StatsFacade statsFacade = new StatsFacade(downloader, repository)

    def "should create stats in the repository using the external stats"() {
        given: "external system returns the current stats for the account"
        downloader.downloadStatsFor(_ as UUID, _ as boolean) >> new ExternalStats(EXAMPLE_UUID, 1, 2)

        and: "repository doesn't contain the stats for the account"
        repository.findByAccountId(EXAMPLE_UUID) >> Optional.empty()

        and:
        Account account = new Account(EXAMPLE_UUID, false)

        when:
        statsFacade.getStatsFor(account)

        then:
        1 * repository.save({ it.views == 1 && it.likes == 2 && it.accountId == EXAMPLE_UUID })
    }

    private static final UUID EXAMPLE_UUID = UUID.randomUUID()

}
