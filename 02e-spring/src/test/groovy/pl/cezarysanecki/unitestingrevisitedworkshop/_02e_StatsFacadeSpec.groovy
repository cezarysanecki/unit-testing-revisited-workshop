package pl.cezarysanecki.unitestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop.Account
import pl.cezarysanecki.unittestingrevisitedworkshop.ExternalStats
import pl.cezarysanecki.unittestingrevisitedworkshop.InconsistentDataEvent
import pl.cezarysanecki.unittestingrevisitedworkshop.Stats
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsConfig
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsDownloader
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsFacade
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryAdditionalStatsSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryEventPublisher
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryImportantStatsSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryStatsRepository
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.SimpleIdGenerator
import spock.lang.Specification

class _02e_StatsFacadeSpec extends Specification {

    private static final def anAccount = new Account(UUID.randomUUID(), true)

    def idGenerator = new SimpleIdGenerator()

    def statsRepository = new InMemoryStatsRepository(() -> idGenerator.nextId())
    def importantStatsSystem = new InMemoryImportantStatsSystem()
    def additionalStatsSystem = new InMemoryAdditionalStatsSystem()
    def eventPublisher = new InMemoryEventPublisher<InconsistentDataEvent>()

    def sut = new StatsConfig().statsFacade(
            statsRepository,
            importantStatsSystem,
            additionalStatsSystem,
            eventPublisher
    )

    def "update stats for existing account using downloaded stats"() {
        given:
        importantStatsSystem.store(new ExternalStats(anAccount.id(), 100, 20))
        and:
        additionalStatsSystem.store(new ExternalStats(anAccount.id(), 100, 20))
        and:
        statsRepository.save(new Stats(anAccount.id(), 10, 2))

        when:
        def result = sut.getStatsFor(anAccount)

        then:
        result.views == 100
        result.likes == 20
    }

    def "create account with downloaded stats when it does not exist"() {
        given:
        importantStatsSystem.store(new ExternalStats(anAccount.id(), 100, 20))
        and:
        additionalStatsSystem.store(new ExternalStats(anAccount.id(), 100, 20))

        when:
        def result = sut.getStatsFor(anAccount)

        then:
        result.views == 100
        result.likes == 20
    }

    def "allow to update stats adhoc"() {
        given:
        statsRepository.save(new Stats(anAccount.id(), 10, 2))

        when:
        def result = sut.updateStatsAdHocFor(anAccount, 200, 10)

        then:
        result.views == 200
        result.likes == 10
    }

    def "do not allow to update stats adhoc when likes are greater than views"() {
        when:
        sut.updateStatsAdHocFor(anAccount, 200, 201)

        then:
        thrown(IllegalArgumentException)
    }
}
