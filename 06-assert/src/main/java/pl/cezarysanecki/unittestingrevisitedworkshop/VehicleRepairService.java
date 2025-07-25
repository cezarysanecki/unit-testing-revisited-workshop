package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static pl.cezarysanecki.unittestingrevisitedworkshop.ServiceResult.Status.SUCCESS;

public class VehicleRepairService {

    private final Map<Parts, Double> partsPrices;
    private final DateProvider dateProvider;

    public VehicleRepairService(
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

    public ServiceResult calculateTotalRepairCost(Vehicle vehicle) {
        int year = vehicle.year();
        int mileage = vehicle.mileage();
        LocalDate lastInspection = vehicle.lastInspectionDate();
        Set<Parts> acceptedParts = new HashSet<>(vehicle.damagedParts());
        LocalDate currentDate = dateProvider.getCurrentDate();
        int currentYear = currentDate.getYear();

        boolean isOldCar = (currentYear - year) > 10;
        boolean isYoungCar = (currentYear - year) < 3;
        boolean highMileage = mileage > 200_000;

        if (isOldCar) {
            acceptedParts.remove(Parts.ENGINE);
        }
        if (!isYoungCar && highMileage) {
            acceptedParts.remove(Parts.SUSPENSION);
        }

        double total = acceptedParts.stream()
                .map(part -> partsPrices.getOrDefault(part, 0.0))
                .reduce(0.0, Double::sum);

        if (currentDate.minusMonths(6).isBefore(lastInspection)) {
            total -= total * 0.15;
        }

        return new ServiceResult(
                vehicle.id(),
                total,
                new HashSet<>(acceptedParts),
                SUCCESS
        );
    }
}
