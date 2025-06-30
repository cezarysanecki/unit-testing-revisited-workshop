package pl.cezarysanecki.unittestingrevisitedworkshop._1

import pl.cezarysanecki.unittestingrevisitedworkshop.InMemoryOrderRepository
import spock.lang.Specification

class OrderPriceCalculatorSpec extends Specification {
    OrderRepository orderRepository = new InMemoryOrderRepository()
    OrderPriceCalculator orderPriceCalculator = new OrderPriceCalculator(orderRepository)

    def "should compute total order price #expectedPriceWithTax for single product of price #productPrice"() {
        given:
        def orderId = 1234
        Order.Product product = new Order.Product(0, 1, productPrice)
        Order order = new Order(orderId, [product])
        orderRepository.save(order)

        when:
        double orderPrice = orderPriceCalculator.computeFinalPriceFor(orderId)

        then:
        orderPrice == expectedPriceWithTax

        where:
        productPrice | expectedPriceWithTax
        10.0d        | 10.0d * 1.23
        20.0d        | 20.0d * 1.23
    }
}
