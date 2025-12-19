package pl.cezarysanecki.unittestingrevisitedworkshop

import spock.lang.Specification

import java.time.Duration
import java.time.Instant
import java.time.Period

class ParcelLockerV2Spec extends Specification {

    Instant NOW = Instant.now()
    Instant PRIOR_END = NOW + Period.ofDays(1)
    Instant BEFORE_END = PRIOR_END - Duration.ofMinutes(1)

    User aClient = new User(UUID.randomUUID())
    User aClient2 = new User(UUID.randomUUID())

    ParcelLockerV2 parcelLocker = ParcelLockerV2.empty()

    def "cannot lock for user when is already locked for other user"() {
        given:
        parcelLocker.lockFor(aClient, NOW)

        when:
        parcelLocker.lockFor(aClient2, NOW + Duration.ofMinutes(5))

        then:
        thrown(IllegalStateException)
    }

    def "can lock locker for user when other user released locker"() {
        given:
        parcelLocker.lockFor(aClient, NOW)

        when:
        parcelLocker.open(aClient, BEFORE_END)

        then:
        parcelLocker.lockFor(aClient2, BEFORE_END + Duration.ofMinutes(5))
    }

    def "cannot lock for user when other user prolonged his lockage"() {
        given:
        parcelLocker.lockFor(aClient, NOW)
        and:
        parcelLocker.prolong(BEFORE_END)

        when:
        parcelLocker.lockFor(aClient2, PRIOR_END + Duration.ofMinutes(5))

        then:
        thrown(IllegalStateException)
    }

}
