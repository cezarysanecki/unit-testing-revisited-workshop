package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class ProductSpec extends Specification {

    def "bought product is not available"() {
        given:
        def product = new Product("123")

        when:
        product.buy()

        then:
        !product.isAvailable()
    }

    def "cannot change price of a bought product"() {
        given:
        def product = new Product("123")
        and:
        product.buy()

        when:
        product.changePrice(100.0)

        then:
        thrown(IllegalStateException)
    }

    def "can change price of an available product"() {
        given:
        def product = new Product("123")

        when:
        product.changePrice(100.0)

        then:
        product.getPrice() == 100.0d
    }

    def "can create unavailable but visible product"() {
        given:
        def product = new Product("catalogue_123")

        expect:
        !product.isAvailable()
    }

}
