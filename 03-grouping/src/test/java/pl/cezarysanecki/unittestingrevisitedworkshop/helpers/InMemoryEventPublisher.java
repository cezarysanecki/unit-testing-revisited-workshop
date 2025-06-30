package pl.cezarysanecki.unittestingrevisitedworkshop.helpers;

import pl.cezarysanecki.unittestingrevisitedworkshop.EventPublisher;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InMemoryEventPublisher implements EventPublisher {

    private static final Queue<Object> STORE = new ConcurrentLinkedQueue<>();

    @Override
    public void publish(Object event) {
        STORE.add(event);
    }

    public Object getLastEvent() {
        return STORE.poll();
    }


}