package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.util.UUID;

public record ExternalStats(UUID accountId, Long views, Long likes) {
}
