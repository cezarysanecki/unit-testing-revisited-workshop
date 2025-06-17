package pl.cezarysanecki.unittestingrevisitedworkshop;

import pl.cezarysanecki.unittestingrevisitedworkshop._1.Order;
import pl.cezarysanecki.unittestingrevisitedworkshop._1.OrderRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderRepository implements OrderRepository {

    private static final Map<Long, Order> STORE = new ConcurrentHashMap<>();

    @Override
    public Order findBy(Long id) {
        return STORE.get(id);
    }

    public void save(Order order) {
        STORE.put(order.orderId(), order);
    }

}
