package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VehicleService {

    private final Map<Parts, Integer> partsPrices;
    private final DateProvider dateProvider;

    public VehicleService(
            DateProvider dateProvider,
            Map<Parts, Integer> partsPrices
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

    public int calculateTotalRepairCost(Vehicle vehicle) {
        int year = vehicle.year();
        int mileage = vehicle.mileage();
        LocalDate lastInspection = vehicle.lastInspectionDate();
        List<Parts> damaged = vehicle.damagedParts();
        LocalDate currentDate = dateProvider.getCurrentDate();
        int currentYear = currentDate.getYear();

        boolean isOldCar = (currentYear - year) > 10;
        boolean isYoungCar = (currentYear - year) < 3;
        boolean highMileage = mileage > 200_000;
        boolean inspectionExpired = lastInspection.isBefore(currentDate.minusYears(2));
        boolean engineDamaged = damaged.contains(Parts.ENGINE);
        boolean gearboxDamaged = damaged.contains(Parts.GEARBOX);
        boolean allPartsDamaged = damaged.containsAll(Set.of(Parts.values()));

        int total;
        int engineCost = 0;
        int gearboxCost = 0;
        int suspensionCost = 0;
        int paintCost = 0;

        for (Parts part : damaged) {
            int partCost = partsPrices.getOrDefault(part, 0);

            if (part == Parts.PAINT && isOldCar) {
                paintCost = partCost;
            } else if (part == Parts.PAINT) {
                paintCost = 0;
            } else if (part == Parts.SUSPENSION && isYoungCar) {
                suspensionCost += 0;
            } else if (part == Parts.SUSPENSION) {
                suspensionCost = partCost;
            } else if (part == Parts.ENGINE) {
                double percentage = 1.0;

                if (highMileage) {
                    percentage += 0.2;
                }
                if (isOldCar) {
                    percentage += 0.3;
                }

                engineCost = (int) Math.round(partCost * percentage);
            } else if (part == Parts.GEARBOX) {
                gearboxCost = partCost;
            }
        }

        total = engineCost + gearboxCost + suspensionCost + paintCost;

        if (engineDamaged && gearboxDamaged) {
            int sum = engineCost + gearboxCost;
            int discount = (int) Math.round(sum * 0.15);
            total -= discount;
        }

        double percentage = 1.0;
        if (inspectionExpired) {
            percentage += 0.1;
        }
        if (allPartsDamaged && isOldCar) {
            percentage -= -0.2;
        }

        return (int) Math.round(total * percentage);
    }
}
