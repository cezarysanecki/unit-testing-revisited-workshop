package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

class DriverLicenseSpec extends Specification {
    def "should create driver license with valid license number #license"() {
        when:
        def driverLicense = DriverLicense.withLicense("ABCDE12")

        then:
        driverLicense.print() == license

        where:
        license << ["ABCDE12", "KRAKO45"]
    }

    def "should fail when license does not match required pattern"() {
        when:
        DriverLicense.withLicense("LICENSE_NOT_MATCHING_PATTERN")

        then:
        thrown(IllegalArgumentException)
    }

    def "should fail when license is empty"() {
        when:
        DriverLicense.withLicense("")

        then:
        thrown(IllegalArgumentException)
    }

    def "should fail when license is null"() {
        when:
        DriverLicense.withLicense(null)

        then:
        thrown(IllegalArgumentException)
    }
}
