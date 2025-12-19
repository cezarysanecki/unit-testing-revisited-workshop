package pl.cezarysanecki.unittestingrevisitedworkshop

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

import java.time.LocalDate

@Builder(prefix = "with", builderStrategy = SimpleStrategy)
class VehicleTestBuilder {

    UUID id = UUID.randomUUID()
    String make = "Skoda"
    String model = "Octavia"
    Integer year = 2015
    String vin = "1HGBH41JXMN109186"
    String color = "Blue"
    Integer mileage = 50_000
    LocalDate lastInspectionDate = LocalDate.of(2020, 1, 1)
    Set<Parts> damagedParts = [Parts.ENGINE, Parts.GEARBOX].toSet()

    static VehicleTestBuilder aVehicle() {
        return new VehicleTestBuilder()
    }

    static VehicleTestBuilder typicalAgedVehicle(LocalDate currentDate) {
        def builder = new VehicleTestBuilder()
        builder.year = currentDate.year - 4
        return builder
    }

    VehicleTestBuilder old(LocalDate currentDate) {
        year = currentDate.year - 15
        return this
    }

    VehicleTestBuilder withDamagedParts(Collection<Parts> damagedParts) {
        this.damagedParts = damagedParts.toSet()
        return this
    }

    VehicleTestBuilder expiredInspectionDate(LocalDate currentDate) {
        this.lastInspectionDate = currentDate.minusYears(2).minusDays(1)
        return this
    }

    VehicleTestBuilder highMileage() {
        this.mileage = 210_000
        return this
    }

    Vehicle build() {
        return new Vehicle(
                id,
                make,
                model,
                year,
                vin,
                color,
                mileage,
                lastInspectionDate,
                damagedParts
        )
    }
}