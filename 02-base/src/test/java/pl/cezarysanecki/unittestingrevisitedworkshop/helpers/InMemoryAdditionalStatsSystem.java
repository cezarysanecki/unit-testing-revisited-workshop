package pl.cezarysanecki.unittestingrevisitedworkshop.helpers;

import pl.cezarysanecki.unittestingrevisitedworkshop.AdditionalStatsSystem;
import pl.cezarysanecki.unittestingrevisitedworkshop.ExternalStats;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAdditionalStatsSystem implements AdditionalStatsSystem {

    private static final Map<UUID, ExternalStats> STORE = new ConcurrentHashMap<>();

    @Override
    public ExternalStats downloadStatsFor(UUID accountId) {
        return STORE.get(accountId);
    }

    public void store(ExternalStats externalStats) {
        STORE.put(externalStats.accountId(), externalStats);
    }

}
