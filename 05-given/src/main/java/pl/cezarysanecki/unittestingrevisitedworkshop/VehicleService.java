package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VehicleService {

    private static final List<String> INCREASED_RISK_MAKES = List.of(
            "AUDI", "BMW"
    );

    private final Map<Parts, Double> partsPrices;
    private final DateProvider dateProvider;

    public VehicleService(
            DateProvider dateProvider,
            Map<Parts, Double> partsPrices
    ) {
        if (!partsPrices.keySet().containsAll(Set.of(Parts.values()))) {
            throw new IllegalArgumentException("All parts must have a price defined.");
        }
        if (partsPrices.values().stream().anyMatch(value -> value <= 0)) {
            throw new IllegalArgumentException("All parts prices must be greater than zero.");
        }
        this.partsPrices = partsPrices;
        this.dateProvider = dateProvider;
    }

    public double calculateTotalRepairCost(Vehicle vehicle) {
        int year = vehicle.year();
        int mileage = vehicle.mileage();
        LocalDate lastInspection = vehicle.lastInspectionDate();
        Set<Parts> damaged = vehicle.damagedParts();
        LocalDate currentDate = dateProvider.getCurrentDate();
        int currentYear = currentDate.getYear();

        boolean isOldCar = (currentYear - year) > 10;
        boolean isYoungCar = (currentYear - year) < 3;
        boolean highMileage = mileage >= 200_000;
        boolean inspectionExpired = lastInspection.isBefore(currentDate.minusYears(2));
        boolean allPartsDamaged = Parts.isAll(damaged);
        boolean isIncreasedRiskMake = INCREASED_RISK_MAKES.contains(vehicle.make());

        double total = 0;
        for (Parts parts : damaged) {
            total += partsPrices.getOrDefault(parts, 0.0);
        }

        double multiplier = 1.0;

        if (isOldCar) {
            multiplier += 0.05;
        } else if (isYoungCar) {
            multiplier -= 0.05;
        }

        if (highMileage) {
            if (isIncreasedRiskMake) {
                multiplier += 0.10;
            } else {
                multiplier += 0.05;
            }
        }

        if (inspectionExpired) {
            multiplier += 0.05;
        }
        if (allPartsDamaged) {
            multiplier -= 0.10;
        }

        return Math.round(total * multiplier * 100.0) / 100.0;
    }
}
