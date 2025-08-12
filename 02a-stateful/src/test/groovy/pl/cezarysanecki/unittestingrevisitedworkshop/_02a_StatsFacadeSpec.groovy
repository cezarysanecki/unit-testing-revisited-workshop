package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class _02a_StatsFacadeSpec extends Specification {

//    def "should set stats for account if exist in external stats"(){
//        given:
//        def account = new Account(UUID.randomUUID(), true)
//        def statsDownloader = Mock(StatsDownloader){
//            downloadStatsFor(account.id()) >> new ExternalStats(account.id(), 5, 10)
//        }
//        def statsRepository = Mock(StatsRepository){
//            findByAccountId(account.id()) >> new Stats()
//        }
//        def statsFacade = new StatsFacade(statsDownloader, statsRepository)
//
//        when:
//        def externalStats = statsDownloader.downloadStatsFor(account.id())
//
//        then:
//        externalStats.likes() == 10
//        externalStats.views() == 5
//        // TODO: Dopisać 1 * statsRepository.save(
//
//    }

    def "should create stats in the repository using the external stats"() {
        given: "external system returns the current stats for the account"
        StatsDownloader downloader = Mock(StatsDownloader)
        downloader.downloadStatsFor(_ as UUID, _ as boolean) >> new ExternalStats(EXAMPLE_UUID, 1, 2)

        and: "repository doesn't contain the stats for the account"
        StatsRepository repository = Mock(StatsRepository)
        repository.findByAccountId(EXAMPLE_UUID) >> Optional.empty()

        and:
        Account account = new Account(EXAMPLE_UUID, false)

        and:
        StatsFacade statsFacade = new StatsFacade(downloader, repository)

        when:
        statsFacade.getStatsFor(account)

        then:
        1 * repository.save({ it.views == 1 && it.likes == 2 && it.accountId == EXAMPLE_UUID })
    }

    private static final UUID EXAMPLE_UUID = UUID.randomUUID()


}
