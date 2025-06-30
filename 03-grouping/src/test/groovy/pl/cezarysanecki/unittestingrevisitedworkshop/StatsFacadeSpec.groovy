package pl.cezarysanecki.unittestingrevisitedworkshop


import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryEventPublisher
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryFirstExternalSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemorySecondExternalSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryStatsRepository
import spock.lang.Specification

class StatsFacadeSpec extends Specification {

    FirstExternalSystem firstExternalSystem = new InMemoryFirstExternalSystem()
    SecondExternalSystem secondExternalSystem = new InMemorySecondExternalSystem()
    EventPublisher eventPublisher = new InMemoryEventPublisher()

    StatsRepository statsRepository = new InMemoryStatsRepository(() -> System.currentTimeMillis())
    StatsDownloader statsDownloader = new StatsDownloader(firstExternalSystem, secondExternalSystem, eventPublisher)

    def "should return stats from existing statistics saved in repository"() {
        given:
        def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001")
        def likes = 100
        def views = 1000
        def account = new Account(accountId, true)
        def statsFacade = new StatsFacade(statsDownloader, statsRepository)
        statsRepository.save(new Stats(12L, accountId, views, likes))

        when:
        def stats = statsFacade.getStatsFor(account)

        then:
        stats.accountId == accountId
        stats.views == views
        stats.likes == likes
    }

    def "should return stats from external systems"() {
        given:
        def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174002")
        def account = new Account(accountId, true)
        def statsFacade = new StatsFacade(statsDownloader, statsRepository)
        def likes = 100
        def views = 1000
        firstExternalSystem.store(new ExternalStats(accountId, views, likes))
        secondExternalSystem.store(new ExternalStats(accountId, views, likes))

        when:
        def stats = statsFacade.getStatsFor(account)

        then:
        stats.accountId == accountId
        stats.views == views
        stats.likes == likes
    }

    def "should create stats when likes are null"() {
        given:
        def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174005")
        def account = new Account(accountId, true)
        def statsFacade = new StatsFacade(statsDownloader, statsRepository)
        def likes = null
        def views = 1000
        firstExternalSystem.store(new ExternalStats(accountId, views, likes))
        secondExternalSystem.store(new ExternalStats(accountId, views, likes))

        when:
        def stats = statsFacade.getStatsFor(account)

        then:
        stats == statsRepository.findByAccountId(accountId).get()
        stats.accountId == accountId
        stats.views == views
        stats.likes == likes
    }

    def "should create stats when views are null"() {
        given:
        def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174006")
        def account = new Account(accountId, true)
        def statsFacade = new StatsFacade(statsDownloader, statsRepository)
        def likes = 100
        def views = null
        firstExternalSystem.store(new ExternalStats(accountId, views, likes))
        secondExternalSystem.store(new ExternalStats(accountId, views, likes))

        when:
        def stats = statsFacade.getStatsFor(account)

        then:
        stats == statsRepository.findByAccountId(accountId).get()
        stats.accountId == accountId
        stats.views == views
        stats.likes == likes
    }

    def "should create stats when likes and views are null"() {
        given:
        def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174007")
        def account = new Account(accountId, true)
        def statsFacade = new StatsFacade(statsDownloader, statsRepository)
        def likes = null
        def views = null
        firstExternalSystem.store(new ExternalStats(accountId, views, likes))
        secondExternalSystem.store(new ExternalStats(accountId, views, likes))

        when:
        def stats = statsFacade.getStatsFor(account)

        then:
        stats == statsRepository.findByAccountId(accountId).get()
        stats.accountId == accountId
        stats.views == views
        stats.likes == likes
    }

    def "should ad-hoc update views and likes in the repository"() {
        given:
        def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174007")
        def account = new Account(accountId, true)
        def statsFacade = new StatsFacade(statsDownloader, statsRepository)
        def views = 10
        def likes = 5

        when:
        def stats = statsFacade.updateStatsAdHocFor(account, views, likes)

        then:
        stats == statsRepository.findByAccountId(accountId).get()
        stats.accountId == accountId
        stats.views == views
        stats.likes == likes
    }

    def "should throw exception when updating stats for invalid parameters: views #views, likes #likes"() {
        given:
        def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174007")
        def account = new Account(accountId, true)
        def statsFacade = new StatsFacade(statsDownloader, statsRepository)

        when:
        def stats = statsFacade.updateStatsAdHocFor(account, views, likes)

        then:
        thrown(IllegalArgumentException)

        where:
        views | likes
        null  | 1
        1     | null
        -5    | 1 // Views < 0
        5     | -10 // Likes < 0
        5     | 100 // Likes > views
    }
}
