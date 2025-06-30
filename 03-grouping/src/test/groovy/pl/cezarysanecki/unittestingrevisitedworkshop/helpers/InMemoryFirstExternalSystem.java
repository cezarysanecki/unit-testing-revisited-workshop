package pl.cezarysanecki.unittestingrevisitedworkshop.helpers;

import pl.cezarysanecki.unittestingrevisitedworkshop.ExternalStats;
import pl.cezarysanecki.unittestingrevisitedworkshop.FirstExternalSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryFirstExternalSystem implements FirstExternalSystem {

    private static final Map<UUID, ExternalStats> STORE = new ConcurrentHashMap<>();

    @Override
    public ExternalStats downloadStatsFor(UUID accountId, boolean premium) {
        return STORE.get(accountId);
    }

    public void store(ExternalStats externalStats) {
        STORE.put(externalStats.accountId(), externalStats);
    }

}
