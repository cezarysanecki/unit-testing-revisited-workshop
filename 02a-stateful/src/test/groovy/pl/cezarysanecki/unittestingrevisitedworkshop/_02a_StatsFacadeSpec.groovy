package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class _02a_StatsFacadeSpec extends Specification {

    def statsDownload = Mock(StatsDownloader)
    def statsRepository = Mock(StatsRepository)

    def sut = new StatsFacade(statsDownload, statsRepository)

    def "update stats for existing account using downloaded stats"() {

    }

    def "create account with downloaded stats when it does not exist"() {

    }

}
