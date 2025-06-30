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

    def "should return stats from existing statistics"() {
        given:
        def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001")
        def likes = 100
        def views = 1000
        def account = new Account(accountId, true)
        def statsFacade = new StatsFacade(statsDownloader, statsRepository)
        statsRepository.save(new Stats(12L,likes, accountId, views ))

        when:
        def stats = statsFacade.getStatsFor(account)

        then:
        stats.accountId == accountId
        stats.views == views
        stats.likes == likes
    }

    def "should return stats from external statistics"() {
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

    def "should create stats when likes is null"() {
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

    def "should create stats when views is null"() {
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


    // TODO:
    //  views, likes albo razem sa null
    //  sa mniejsze od 0
    //  przypadek ze jest rowny 0
    //  likes > views
    //  likes == views
    //  istnieje statysyka
    //  nie ma statystyki

    def "should update statistics based on new views and likes"() {
        given:
            def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174011")
            def account = new Account(accountId, true)
            def statsFacade = new StatsFacade(statsDownloader, statsRepository)
            statsRepository.save(new Stats(12L,likes, accountId, views ))

        when:
            def stats = statsFacade.updateStatsAdHocFor(account, views * 2, likes * 2)

        then:
            stats.accountId == accountId
            stats.views == 2 * views
            stats.likes == 2 *likes

        where:
            likes | views
            0     | 0
            100   | 1000
    }

    def "should update new created statistic when is not existing"() {
        given:
            def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174012")
            def account = new Account(accountId, true)
            def statsFacade = new StatsFacade(statsDownloader, statsRepository)

        when:
            def stats = statsFacade.updateStatsAdHocFor(account, views * 2, likes * 2)

        then:
            stats.accountId == accountId
            stats.views == 2 * views
            stats.likes == 2 *likes

        where:
            likes | views
            0     | 0
            100   | 1000
    }

    def "should throw exception when updated data is incorrect"() {
        given:
            def accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174013")
            def account = new Account(accountId, true)
            def statsFacade = new StatsFacade(statsDownloader, statsRepository)

        when:
            statsFacade.updateStatsAdHocFor(account, views, likes)

        then:
            thrown(IllegalArgumentException)

        where:
            likes | views
            1000  | 100
            -1000 | -2000
            -1000 | 100
            100   | -100
            -1000 | -1000
            null  | null
    }
}
