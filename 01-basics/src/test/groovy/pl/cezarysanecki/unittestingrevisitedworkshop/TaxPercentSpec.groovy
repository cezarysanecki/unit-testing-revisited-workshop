package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class TaxPercentSpec extends Specification {

    def "tax percent #percent% should be converted to decimal fraction equaled #decimalFraction"() {
        given:
        def tax = new TaxPercent(percent)

        when:
        def result = tax.toDecimalFraction()

        then:
        result == decimalFraction

        where:
        percent | decimalFraction
        100.0G  | 1.00G
        23.0G   | 0.23G
    }

    def "print tax percent as rounded value with percent sign"() {
        given:
        def tax = new TaxPercent(10.237G)

        when:
        def result = tax.toString()

        then:
        result == "10.24%"
    }

    def "tax percent should be greater then zero"() {
        when:
        new TaxPercent(-1.0G)

        then:
        thrown(IllegalArgumentException)
    }

}
