package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

import java.time.LocalDate

class VehicleServiceSpec extends Specification {

    LocalDate CURRENT_DATE = LocalDate.now()

    FakeDateProvider dateProvider = new FakeDateProvider(CURRENT_DATE)

    def "count full price for paint when vehicle is too old"() {

    }

    def "take into account that engine repair cost is more expensive for too old vehicle"() {

    }

    def "provide discount for too old vehicle with all damaged parts"() {

    }

}
