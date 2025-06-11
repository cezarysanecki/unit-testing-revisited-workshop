package pl.cezarysanecki.unittestingrevisitedworkshop.calculator

import spock.lang.Specification

class OrderPriceCalculatorSpec extends Specification {

    def orderRepository = new InMemoryOrderRepository()

    def sut = new OrderPriceCalculator(orderRepository)

    def "calculate price of order taking into account tax"() {

    }

}
