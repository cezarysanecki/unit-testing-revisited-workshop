package pl.cezarysanecki.unittestingrevisitedworkshop._1;

public class OrderPriceCalculator {

    private final OrderRepository orderRepository;

    public OrderPriceCalculator(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public double computeFinalPriceFor(Long orderId) {
        Order order = orderRepository.findBy(orderId);

        return ExternalPriceCalculator.computeFinalPrice(order.totalPrice());
    }

}
