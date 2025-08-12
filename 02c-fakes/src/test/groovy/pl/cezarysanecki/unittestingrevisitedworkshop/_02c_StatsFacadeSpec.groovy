package pl.cezarysanecki.unittestingrevisitedworkshop

import org.springframework.util.IdGenerator
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryAdditionalStatsSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryEventPublisher
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryImportantStatsSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryStatsRepository
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.SimpleIdGenerator
import spock.lang.Specification

import java.util.function.Supplier

class _02b_StatsFacadeSpec extends Specification {

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

    def "receive stats have consistent values with external systems"() {
        given:
        importantStatsSystem.store(new ExternalStats(ACCOUNT.id(), 100, 20))
        additionalStatsSystem.store(new ExternalStats(ACCOUNT.id(), 100, 20))

        when:
        def result = statsFacade.getStatsFor(ACCOUNT)

        then:
        result.accountId
        result.views == 100
        result.likes == 20
    }

//    def "inform about inconsistency in external systems"() {
//        given:
//        importantStatsSystem.downloadStatsFor(ACCOUNT.id(), ACCOUNT.premium()) >> new ExternalStats(ACCOUNT.id(), 100, 20)
//        additionalStatsSystem.downloadStatsFor((ACCOUNT.id())) >> new ExternalStats(ACCOUNT.id(), 100, 30)
//        statsRepository.save(_ as Stats) >> { Stats stats ->
//            return stats
//        }
//        statsRepository.findByAccountId(ACCOUNT.id()) >> Optional.empty()
//
//        when:
//        sut.getStatsFor(ACCOUNT)
//
//        then:
//        1 * eventPublisher.publish(new InconsistentDataEvent(ACCOUNT.id()))
//    }

}
