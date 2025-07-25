package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.util.Set;
import java.util.UUID;

public record ServiceResult(
        UUID handledVehicle,
        double cost,
        Set<Parts> acceptedParts,
        Status status
) {
    enum Status {
        SUCCESS,
        REJECTED
    }
}
