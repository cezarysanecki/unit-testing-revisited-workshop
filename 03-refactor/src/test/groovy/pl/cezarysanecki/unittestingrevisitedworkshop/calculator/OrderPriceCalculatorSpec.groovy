package pl.cezarysanecki.unittestingrevisitedworkshop.calculator

import spock.lang.Specification

class OrderPriceCalculatorSpec extends Specification {

    def orderRepository = new InMemoryOrderRepository()
    def externalPriceCalculator = new ExternalPriceCalculatorInterface() {
        @Override
        double computeFinalPrice(double price) {
            return price * 1.2
        }
    }

    def sut = new OrderPriceCalculator(orderRepository, externalPriceCalculator)

    def "calculate price of order taking into account tax"() {
        given:
        def order = new Order(12L, [
                new Order.Product(133L, 10, 2.0),
        ])
        and:
        orderRepository.save(order)

        when:
        def result = sut.computeFinalPriceFor(order.orderId())

        then:
        result == 24.0D
    }

}
