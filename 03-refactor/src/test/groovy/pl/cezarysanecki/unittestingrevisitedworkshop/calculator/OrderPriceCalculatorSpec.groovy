package pl.cezarysanecki.unittestingrevisitedworkshop.calculator

import spock.lang.Specification

class OrderPriceCalculatorSpec extends Specification {
    def repository = new InMemoryOrderRepository()
    def orderPriceCalculator = new OrderPriceCalculator(repository)

    def "price should be 0 when no Product in Order"(){
        given:
            repository.save(new Order(1, []))

        when:
            def price = orderPriceCalculator.computeFinalPriceFor(repository.findBy(1).orderId())

        then:
            assert price == 0

    }

}
