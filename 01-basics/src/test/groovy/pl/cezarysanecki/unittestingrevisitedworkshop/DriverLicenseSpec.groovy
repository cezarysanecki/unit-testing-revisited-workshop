package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class DriverLicenseSpec extends Specification {

    def "should throw Exception when driverLicense #usecase"() {
        when:
        DriverLicense.withLicense(argument)

        then:
        thrown(IllegalArgumentException)

        where:
        usecase              | argument
        "not matched regex"  | "invalid"
        "null"               | null
        "empty"              | ""
    }

    def "should create driverLicense when validation passed"(){
        given:
        def driverLicense = "ABCDE12"

        when:
        def license = DriverLicense.withLicense(driverLicense)

        then:
        assert license != null
        assert license.print() == "ABCDE12"

    }

    def "should instantiate new License without validation"() {
        given:
        def driverLicense = "testing_value"

        when:
        def license = DriverLicense.withoutValidation(driverLicense)

        then:
        assert license.print() == "testing_value"
    }


}
