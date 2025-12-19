package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

import java.time.LocalDate

import static java.util.EnumSet.allOf
import static pl.cezarysanecki.unittestingrevisitedworkshop.Parts.ENGINE
import static pl.cezarysanecki.unittestingrevisitedworkshop.Parts.GEARBOX
import static pl.cezarysanecki.unittestingrevisitedworkshop.Parts.PAINT
import static pl.cezarysanecki.unittestingrevisitedworkshop.Parts.SUSPENSION
import static pl.cezarysanecki.unittestingrevisitedworkshop.VehicleTestBuilder.aVehicle
import static pl.cezarysanecki.unittestingrevisitedworkshop.VehicleTestBuilder.typicalAgedVehicle

class VehicleRepairServiceSpec extends Specification {

    private static final LocalDate CURRENT_DATE = LocalDate.of(2020, 10, 15)
    private static final Map<Parts, Double> EXAMPLE_PARTS_PRICES = Map.of(
            ENGINE, 5_000.0d,
            GEARBOX, 4_000.0d,
            SUSPENSION, 10_000.0d,
            PAINT, 1_000.0d,
    )

    def dateProvider = new FakeDateProvider(CURRENT_DATE)

    def sut = new VehicleRepairService(dateProvider, EXAMPLE_PARTS_PRICES)

    def "reject repairing damaged parts of increased risk vehicle make"() {
        given:
        def vehicle = typicalAgedVehicle(CURRENT_DATE)
                .withMake("BMW")
                .withDamagedParts([GEARBOX])
                .build()

        when:
        def result = sut.prepareRepairEvaluation(vehicle)

        then:
        ServiceResultAssertObject.of(result)
                .theSameVehicle(vehicle.id())
                .rejected()
                .noCharge()
                .noHandledParts()
    }

    def "accept all parts except engine for old vehicle"() {
        given:
        def vehicle = aVehicle()
                .withDamagedParts(allOf(Parts.class))
                .old(CURRENT_DATE)
                .build()

        when:
        def result = sut.prepareRepairEvaluation(vehicle)

        then:
        ServiceResultAssertObject.of(result)
                .theSameVehicle(vehicle.id())
                .accepted()
                .charge(15_000.0d)
                .allPartsBut(ENGINE)
    }

    def "include discount when all parts handled by service are accepted"() {
        given:
        def vehicle = typicalAgedVehicle(CURRENT_DATE)
                .withDamagedParts(allOf(Parts.class))
                .build()

        when:
        def result = sut.prepareRepairEvaluation(vehicle)

        then:
        ServiceResultAssertObject.of(result)
                .theSameVehicle(vehicle.id())
                .accepted()
                .charge(18_000.0d)
                .allParts()
    }

}