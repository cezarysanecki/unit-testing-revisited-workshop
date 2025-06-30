package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class StatsFacadeGetStatsFromRepositorySpec extends Specification implements StatsRepositoryFixture {
    def "should return stats from existing statistics saved in repository"() {
        given:
        def accountId = UUID.randomUUID()
        def likes = 100
        def views = 1000
        def statsId = 12L
        def account = new Account(accountId, true)
        def statsFacade = new StatsFacade(statsDownloader, statsRepository)
        statsRepository.save(new Stats(statsId, accountId, views, likes))

        when:
        def stats = statsFacade.getStatsFor(account)

        then:
        stats.id == statsId
        stats.accountId == accountId
        stats.views == views
        stats.likes == likes
    }
}
