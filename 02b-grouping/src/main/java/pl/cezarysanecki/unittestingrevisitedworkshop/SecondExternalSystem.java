package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.util.UUID;

public interface SecondExternalSystem {
    ExternalStats downloadStatsFor(UUID accountId);
}
