package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.util.UUID;

// Contract: we send statistics for a given account
public interface FirstExternalSystem {
    ExternalStats downloadStatsFor(UUID accountId);
}
