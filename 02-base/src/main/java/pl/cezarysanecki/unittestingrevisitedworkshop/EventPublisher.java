package pl.cezarysanecki.unittestingrevisitedworkshop;

public interface EventPublisher<T> {

    void publish(Object event);

}
