package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

import java.time.LocalDate

import static pl.cezarysanecki.unittestingrevisitedworkshop.Parts.*

class VehicleServiceSpec extends Specification {

    Map<Parts, Double> EXAMPLE_PARTS_PRICES = Map.of(
            ENGINE, 5_000.0d,
            GEARBOX, 4_000.0d,
            SUSPENSION, 10_000.0d,
            PAINT, 1_000.0d,
    )

    LocalDate CURRENT_DATE = LocalDate.now()

    FakeDateProvider dateProvider = new FakeDateProvider(CURRENT_DATE)

    def "take into account discount for all damaged parts"() {

    }

    def "take into account rise for expired inspection"() {

    }

    def "take into account rise for high milage BMW vehicle"() {

    }

}
