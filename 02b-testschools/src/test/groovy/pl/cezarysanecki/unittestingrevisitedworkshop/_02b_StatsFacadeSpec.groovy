package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class _02b_StatsFacadeSpec extends Specification {

    def importantStatsSystem = Mock(ImportantStatsSystem)
    def additionalStatsSystem = Mock(AdditionalStatsSystem)
    def statsRepository = Mock(StatsRepository)
    def eventPublisher = Mock(EventPublisher<InconsistentDataEvent>)

    def statsDownloader = new StatsDownloader(
             importantStatsSystem,
            additionalStatsSystem,
            eventPublisher
    )

    def sut = new StatsFacade(
            statsDownloader,
            statsRepository
    )

    private static final ACCOUNT = new Account(UUID.randomUUID(), true)

    def "receive stats have consistent values with external systems"() {
        given:
        importantStatsSystem.downloadStatsFor(ACCOUNT.id()) >> new ExternalStats(ACCOUNT.id(), 100, 20)
        additionalStatsSystem.downloadStatsFor((ACCOUNT.id())) >> new ExternalStats(ACCOUNT.id(), 100, 20)
        statsRepository.save(_ as Stats) >> { Stats stats ->
            return stats
        }
        statsRepository.findByAccountId(ACCOUNT.id()) >> Optional.empty()

        when:
        def result = sut.getStatsFor(ACCOUNT)

        then:
        result.accountId
        result.views == 100
        result.likes == 20
    }

    def "inform about inconsistency in external systems"() {
        given:
        importantStatsSystem.downloadStatsFor(ACCOUNT.id()) >> new ExternalStats(ACCOUNT.id(), 100, 20)
        additionalStatsSystem.downloadStatsFor((ACCOUNT.id())) >> new ExternalStats(ACCOUNT.id(), 100, 30)
        statsRepository.save(_ as Stats) >> { Stats stats ->
            return stats
        }
        statsRepository.findByAccountId(ACCOUNT.id()) >> Optional.empty()

        when:
        sut.getStatsFor(ACCOUNT)

        then:
        1 * eventPublisher.publish(new InconsistentDataEvent(ACCOUNT.id()))
    }

}
