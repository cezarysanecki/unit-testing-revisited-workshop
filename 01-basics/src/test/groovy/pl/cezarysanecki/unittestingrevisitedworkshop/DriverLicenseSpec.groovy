package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class DriverLicenseSpec extends Specification {

    def "allow to create driver licence without validation"() {
        when:
        def driverLicence = DriverLicense.withoutValidation("123")

        then:
        driverLicence.print() == "123"
    }

    def "fail to create driver licence when value #"() {
        when:
        DriverLicense.withLicense(value)

        then:
        thrown(IllegalArgumentException)

        where:
        value  | testCase
        ""     | "is empty"
        null   | "is null"
        "fake" | "does not match pattern"
    }

    def "allow to create driver licence matching pattern"() {
        when:
        def driverLicence = DriverLicense.withLicense("ABCDE12")

        then:
        driverLicence.print() == "ABCDE12"
    }

}
