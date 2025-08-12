package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

import java.time.Duration
import java.time.Instant
import java.time.Period

class ParcelLockerV2Spec extends Specification {

    Instant NOW = Instant.now()
    Instant BEFORE_END = Instant.now() + Period.ofDays(1) - Duration.ofMinutes(1)

    User aClient = new User(UUID.randomUUID())

    ParcelLockerV2 parcelLocker = ParcelLockerV2.empty()

    def "lock locker for user"() {
        when:
        parcelLocker.lockFor(aClient, NOW)

        then:
        parcelLocker.getAssignedTo() == aClient
        parcelLocker.getLockUntil() == NOW + Period.ofDays(1)
        parcelLocker.prolonged() == 0
    }

    def "open locker for user in valid time"() {
        given:
        parcelLocker.lockFor(aClient, NOW)

        when:
        parcelLocker.open(aClient, BEFORE_END)

        then:
        parcelLocker.getAssignedTo() == null
        parcelLocker.getLockUntil() == null
        parcelLocker.prolonged() == 0
    }

    def "prolong locker for user"() {
        given:
        parcelLocker.lockFor(aClient, NOW)

        when:
        parcelLocker.prolong(BEFORE_END)

        then:
        parcelLocker.getAssignedTo() == aClient
        parcelLocker.getLockUntil() == NOW + Period.ofDays(2)
        parcelLocker.prolonged() == 1
    }

}
