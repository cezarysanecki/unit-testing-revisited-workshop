package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record Vehicle(
        UUID id,
        String make,
        String model,
        Integer year,
        String vin,
        String color,
        Integer mileage,
        LocalDate lastInspectionDate,
        Set<Parts> damagedParts
) {
}
