package pl.cezarysanecki.unittestingrevisitedworkshop.helpers;

import pl.cezarysanecki.unittestingrevisitedworkshop.EventPublisher;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InMemoryEventPublisher<T> implements EventPublisher<T> {

    private final Queue<T> STORE = new ConcurrentLinkedQueue<>();

    @Override
    public void publish(T event) {
        STORE.add(event);
    }

    public T getLastEvent() {
        return STORE.poll();
    }


}