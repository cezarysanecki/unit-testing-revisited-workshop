package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.util.UUID;

public interface ImportantStatsSystem {
    ExternalStats downloadStatsFor(UUID accountId);
}
