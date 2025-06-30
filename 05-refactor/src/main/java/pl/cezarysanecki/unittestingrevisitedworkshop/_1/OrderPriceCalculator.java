package pl.cezarysanecki.unittestingrevisitedworkshop._1;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderPriceCalculator {

    private final OrderRepository orderRepository;
    private final ExternalPriceCalculatorWrapper externalPriceCalculatorWrapper;

    public double computeFinalPriceFor(Long orderId) {
        Order order = orderRepository.findBy(orderId);

        return externalPriceCalculatorWrapper.computeFinalPrice(order.totalPrice());
    }

}
