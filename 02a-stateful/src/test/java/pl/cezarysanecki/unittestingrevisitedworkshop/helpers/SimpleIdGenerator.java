package pl.cezarysanecki.unittestingrevisitedworkshop.helpers;

import java.util.concurrent.atomic.AtomicLong;

public class SimpleIdGenerator {

    private AtomicLong idGenerator = new AtomicLong();

    public Long nextId() {
        return idGenerator.incrementAndGet();
    }

}
