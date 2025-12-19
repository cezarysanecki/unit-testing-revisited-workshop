package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

import java.time.LocalDate

import static pl.cezarysanecki.unittestingrevisitedworkshop.Parts.ENGINE
import static pl.cezarysanecki.unittestingrevisitedworkshop.Parts.GEARBOX
import static pl.cezarysanecki.unittestingrevisitedworkshop.Parts.PAINT
import static pl.cezarysanecki.unittestingrevisitedworkshop.Parts.SUSPENSION
import static pl.cezarysanecki.unittestingrevisitedworkshop.VehicleTestBuilder.typicalAgedVehicle

class VehicleServiceSpec extends Specification {

    private static final LocalDate CURRENT_DATE = LocalDate.of(2020, 10, 15)
    private static final Map<Parts, Double> EXAMPLE_PARTS_PRICES = Map.of(
            ENGINE, 5_000.0d,
            GEARBOX, 4_000.0d,
            SUSPENSION, 10_000.0d,
            PAINT, 1_000.0d,
    )

    def dateProvider = new FakeDateProvider(CURRENT_DATE)

    def sut = new VehicleService(dateProvider, EXAMPLE_PARTS_PRICES)

    def "take into account discount for all damaged parts"() {
        given:
        def vehicle = typicalAgedVehicle(CURRENT_DATE)
                .withDamagedParts(EnumSet.allOf(Parts.class))
                .build()

        when:
        def result = sut.calculateTotalRepairCost(vehicle)

        then:
        result == 18_000.0d
    }

    def "take into account rise for expired inspection"() {
        given:
        def vehicle = typicalAgedVehicle(CURRENT_DATE)
                .withDamagedParts([GEARBOX])
                .expiredInspectionDate(CURRENT_DATE)
                .build()

        when:
        def result = sut.calculateTotalRepairCost(vehicle)

        then:
        result == 4_200.0d
    }

    def "take into account rise for high mileage BMW vehicle"() {
        given:
        def vehicle = typicalAgedVehicle(CURRENT_DATE)
                .withMake("BMW")
                .withDamagedParts([GEARBOX])
                .highMileage()
                .build()

        when:
        def result = sut.calculateTotalRepairCost(vehicle)

        then:
        result == 4_400.0d
    }

}