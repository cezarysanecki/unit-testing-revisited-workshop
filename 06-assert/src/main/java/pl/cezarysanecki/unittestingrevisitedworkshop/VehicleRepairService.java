package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static pl.cezarysanecki.unittestingrevisitedworkshop.ServiceResult.Status.REJECTED;
import static pl.cezarysanecki.unittestingrevisitedworkshop.ServiceResult.Status.SUCCESS;

public class VehicleRepairService {

    private static final List<String> INCREASED_RISK_MAKES = List.of(
            "AUDI", "BMW"
    );

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

    public ServiceResult prepareRepairEvaluation(Vehicle vehicle) {
        int year = vehicle.year();
        Set<Parts> damaged = vehicle.damagedParts();
        LocalDate currentDate = dateProvider.getCurrentDate();
        int currentYear = currentDate.getYear();

        boolean isOldCar = (currentYear - year) > 10;
        boolean isIncreasedRiskMake = INCREASED_RISK_MAKES.contains(vehicle.make());

        if (isIncreasedRiskMake) {
            return new ServiceResult(
                    vehicle.id(),
                    0.0d,
                    Set.of(),
                    REJECTED
            );
        }

        Set<Parts> acceptedParts = new HashSet<>();

        for (Parts damagedPart : damaged) {
            if (isOldCar && damagedPart == Parts.ENGINE) {
                continue;
            }

            acceptedParts.add(damagedPart);
        }

        double total = acceptedParts.stream()
                .map(acceptedPart -> partsPrices.getOrDefault(acceptedPart, 0.0d))
                .reduce(0.0d, Double::sum);

        boolean allPartsAccepted = Parts.isAll(acceptedParts);
        if (allPartsAccepted) {
            total *= 0.9;
        }

        return new ServiceResult(
                vehicle.id(),
                total,
                acceptedParts,
                SUCCESS
        );
    }
}
