package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

import java.time.LocalDate

class VehicleServiceSpec extends Specification {

    LocalDate CURRENT_DATE = LocalDate.now()

    FakeDateProvider dateProvider = new FakeDateProvider(CURRENT_DATE)

    def "calculate full price for paint when vehicle is too old (older than 10 years)"() {

    }

    def "take into account that engine repair cost is 30% more expensive for an old vehicle"() {
        given:
        def enginePrice = 1000.0d
        def partsPrices = [
                (Parts.ENGINE): enginePrice,
                (Parts.GEARBOX): 800.0d,
                (Parts.SUSPENSION): 600.0d,
                (Parts.PAINT): 400.0d
        ]
        def vehicleService = new VehicleService(dateProvider, partsPrices)

        and: "a vehicle that is older than 10 years (11 years old)"
        def oldVehicle = new Vehicle(
                UUID.randomUUID(),
                "Toyota",
                "Camry",
                2004, // 11 years old in 2025
                "1HGBH41JXMN109186",
                "Blue",
                150000, // under 200k to avoid high mileage markup
                CURRENT_DATE.minusMonths(6), // recent inspection to avoid penalty
                Set.of(Parts.ENGINE) // only engine damage
        )

        when:
        def totalCost = vehicleService.calculateTotalRepairCost(oldVehicle)

        then: "engine repair cost should be 30% more expensive for old vehicle"
        def expectedCost = (double)(enginePrice * 1.3) // 30% markup for old vehicle
        totalCost == expectedCost
    }

    def "provide discount for too old vehicle with all damaged parts"() {

    }

}
