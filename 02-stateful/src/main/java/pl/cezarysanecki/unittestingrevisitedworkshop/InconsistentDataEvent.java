package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.util.UUID;

public record InconsistentDataEvent(UUID accountId) {
}
