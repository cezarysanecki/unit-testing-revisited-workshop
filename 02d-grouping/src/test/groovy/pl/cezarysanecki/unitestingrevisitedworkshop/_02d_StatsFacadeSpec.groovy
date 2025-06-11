package pl.cezarysanecki.unitestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop.Account
import pl.cezarysanecki.unittestingrevisitedworkshop.InconsistentDataEvent
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsDownloader
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsFacade
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryAdditionalStatsSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryEventPublisher
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryImportantStatsSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryStatsRepository
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.SimpleIdGenerator
import spock.lang.Specification

class _02d_StatsFacadeSpec extends Specification {

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

    def "update stats for existing account using downloaded stats"() {

    }

    def "create account with downloaded stats when it does not exist"() {

    }

    def "allow to update stats adhoc"() {

    }

    def "do not allow to update stats adhoc when likes are greater than views"() {

    }

}
