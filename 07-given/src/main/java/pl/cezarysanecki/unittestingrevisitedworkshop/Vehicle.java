package pl.cezarysanecki.unittestingrevisitedworkshop;

import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Vehicle(
        @NonNull UUID id,
        @NonNull String make,
        @NonNull String model,
        @NonNull Integer year,
        @NonNull String vin,
        @NonNull String color,
        @NonNull Integer mileage,
        @NonNull LocalDate lastInspectionDate,
        @NonNull List<Parts> damagedParts
) {
}
