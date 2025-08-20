package pl.cezarysanecki.unittestingrevisitedworkshop.parcel

import spock.lang.Specification

class ParcelLockerSpec extends Specification {

    User aClient = new User(UUID.randomUUID())

    ParcelLocker parcelLocker = ParcelLocker.empty()

    def "parcel locker is assigned to user when it is locked for him"() {

    }

    def "can release parcel locker if it is assigned to user"() {

    }

    def "can prolong lock if user is in time window to be able to do this"() {

    }

}
