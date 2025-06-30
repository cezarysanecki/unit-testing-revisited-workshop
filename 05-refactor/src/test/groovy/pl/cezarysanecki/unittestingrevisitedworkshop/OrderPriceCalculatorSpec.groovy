package pl.cezarysanecki.unittestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop._1.ExternalPriceCalculatorWrapper
import pl.cezarysanecki.unittestingrevisitedworkshop._1.FakeExternalPriceCalculatorWrapper
import pl.cezarysanecki.unittestingrevisitedworkshop._1.Order
import pl.cezarysanecki.unittestingrevisitedworkshop._1.OrderPriceCalculator
import pl.cezarysanecki.unittestingrevisitedworkshop._1.OrderRepository

import spock.lang.Specification;

class OrderPriceCalculatorSpec extends Specification {

    OrderRepository orderRepository = new InMemoryOrderRepository()
    FakeExternalPriceCalculatorWrapper priceCalculatorWrapper = new FakeExternalPriceCalculatorWrapper()

    def "should compute price for existing order"() {
        given:
        def orderId = 1L
        def products = [
            new Order.Product(1L, 1, 20.00),
            new Order.Product(2L, 1, 30.00)
        ]
        def order = new Order(orderId, products)
        orderRepository.save(order)
        def sut = new OrderPriceCalculator(orderRepository, priceCalculatorWrapper)


        when:
        def result = sut.computeFinalPriceFor(orderId)

        then:
        result == 61.5d
    }
}
