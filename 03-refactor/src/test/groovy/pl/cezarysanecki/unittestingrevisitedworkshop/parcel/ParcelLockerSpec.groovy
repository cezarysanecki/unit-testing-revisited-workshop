package pl.cezarysanecki.unittestingrevisitedworkshop.parcel

import spock.lang.Specification

import java.time.Duration
import java.time.Instant
import java.time.Period

class ParcelLockerSpec extends Specification {

    Instant NOW = Instant.now()
    Instant PRIOR_END = NOW + Period.ofDays(1)
    Instant BEFORE_END = PRIOR_END - Duration.ofMinutes(1)

    User aClient = new User(UUID.randomUUID())

    ParcelLocker parcelLocker = ParcelLocker.empty()

    def "parcel locker is assigned to user when it is locked for him"() {
        when:
        parcelLocker.lockFor(aClient, NOW)

        then:
        parcelLocker.getAssignedTo() == aClient
        parcelLocker.getLockUntil() == PRIOR_END
    }

    def "can release parcel locker if it is assigned to user"() {
        given:
        parcelLocker.lockFor(aClient, NOW)

        when:
        parcelLocker.release()

        then:
        parcelLocker.getAssignedTo() == null
        parcelLocker.getLockUntil() == null
    }

    def "can prolong lock if user is in time window to be able to do this"() {
        given:
        parcelLocker.lockFor(aClient, NOW)

        when:
        parcelLocker.prolong(BEFORE_END)

        then:
        noExceptionThrown()
    }

}