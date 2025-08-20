package pl.cezarysanecki.unittestingrevisitedworkshop.calculator;

public class OrderPriceCalculator {

    private final OrderRepository orderRepository;
    private final ExternalPriceCalculatorInterface externalPriceCalculatorInterface;

    public OrderPriceCalculator(
            OrderRepository orderRepository,
            ExternalPriceCalculatorInterface externalPriceCalculatorInterface
    ) {
        this.orderRepository = orderRepository;
        this.externalPriceCalculatorInterface = externalPriceCalculatorInterface;
    }

    public double computeFinalPriceFor(Long orderId) {
        Order order = orderRepository.findBy(orderId);

        return externalPriceCalculatorInterface.computeFinalPrice(order.totalPrice());
    }

}
