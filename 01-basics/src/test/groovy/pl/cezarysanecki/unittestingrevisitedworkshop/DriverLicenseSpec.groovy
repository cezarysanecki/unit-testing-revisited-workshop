package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class DriverLicenseSpec extends Specification {

    def "should create license with correct driver license information"() {
        when:
        def driverLicense = DriverLicense.withLicense(license)

        then:
        driverLicense.print() == license

        where:
        license << ["POZNA22"]

    }

    def "should throw exception when driver license is incorrect"() {
        when:
        DriverLicense.withLicense(license)

        then:
        thrown(IllegalArgumentException)

        where:
        license << [null, "", "12345PO", "POZNAN", "POZNAN1234567890", "POZNAN12345678901234567890"]
    }
}
