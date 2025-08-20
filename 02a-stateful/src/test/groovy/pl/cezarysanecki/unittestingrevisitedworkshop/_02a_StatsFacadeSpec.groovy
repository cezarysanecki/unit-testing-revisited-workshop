package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class _02a_StatsFacadeSpec extends Specification {

    private static final def anAccount = new Account(UUID.randomUUID(), true)

    def statsDownload = Mock(StatsDownloader)
    def statsRepository = Mock(StatsRepository)

    def sut = new StatsFacade(statsDownload, statsRepository)

    def "update stats for existing account using downloaded stats"() {
        given:
        statsDownload.downloadStatsFor(anAccount.id()) >> new ExternalStats(anAccount.id(), 100, 20)
        and:
        statsRepository.findByAccountId(anAccount.id()) >> Optional.of(new Stats(anAccount.id(), 10, 2))
        and:
        statsRepository.save(_ as Stats) >> { Stats stats ->
            return stats
        }

        when:
        def result = sut.getStatsFor(anAccount)

        then:
        result.views == 100
        result.likes == 20
    }

    def "create account with downloaded stats when it does not exist"() {
        given:
        statsDownload.downloadStatsFor(anAccount.id()) >> new ExternalStats(anAccount.id(), 100, 20)
        and:
        statsRepository.findByAccountId(anAccount.id()) >> Optional.empty()
        and:
        statsRepository.save(_ as Stats) >> { Stats stats ->
            return stats
        }

        when:
        def result = sut.getStatsFor(anAccount)

        then:
        result.views == 100
        result.likes == 20
    }

}
