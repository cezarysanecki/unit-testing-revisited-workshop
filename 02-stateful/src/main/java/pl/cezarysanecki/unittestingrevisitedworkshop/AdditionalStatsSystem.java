package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.util.UUID;

public interface AdditionalStatsSystem {
    ExternalStats downloadStatsFor(UUID accountId);
}
