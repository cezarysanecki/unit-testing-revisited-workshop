package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.util.UUID;

public interface FirstExternalSystem {
    ExternalStats downloadStatsFor(UUID accountId);
}
