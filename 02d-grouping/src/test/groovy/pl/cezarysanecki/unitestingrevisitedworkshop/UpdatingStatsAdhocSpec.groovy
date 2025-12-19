package pl.cezarysanecki.unitestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop.Account
import pl.cezarysanecki.unittestingrevisitedworkshop.InconsistentDataEvent
import pl.cezarysanecki.unittestingrevisitedworkshop.Stats
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsDownloader
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsFacade
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryAdditionalStatsSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryEventPublisher
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryImportantStatsSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryStatsRepository
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.SimpleIdGenerator
import spock.lang.Specification

class UpdatingStatsAdhocSpec extends Specification {

    private static final def anAccount = new Account(UUID.randomUUID(), true)

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
