package pl.cezarysanecki.unittestingrevisitedworkshop._2

import spock.lang.Specification

import java.time.Duration
import java.time.Instant

class ParcelLockerSpec extends Specification {

    def user1 = new User(UUID.randomUUID())
    def user2 = new User(UUID.randomUUID())
    def anyTimestamp = Instant.ofEpochMilli(1L)

    def "should create empty locker"() {
        when:
        def locker = ParcelLocker.empty()

        then:
        locker.assignedTo == null
        locker.lockUntil == null
    }

    def "should lock free parcel locker for a user"() {
        given:
        def locker = ParcelLocker.empty()
        def oneDayAfter = anyTimestamp + Duration.ofDays(1)

        when:
        locker.lockFor(user1, anyTimestamp)

        then:
        locker.assignedTo == user1
        locker.lockUntil == oneDayAfter
    }

    def "should throw exception when locking when already locked by another user"() {
        given:
        def locker = new ParcelLocker(user1, Instant.now())

        when:
        locker.lockFor(user2, Instant.now())

        then:
        thrown(IllegalStateException)
        locker.assignedTo == user1 // should remain unchanged
    }

    def "should prolong locker assignment by one day"() {
        given:
        def lockedAt = Instant.now()
        def lockedUntil = lockedAt + Duration.ofDays(1L)
        def prolongedAt = lockedUntil - Duration.ofMinutes(10)  // less than 15 minutes before the expiration date
        def prolongedLockedUntil = lockedUntil + Duration.ofDays(1L)

        def locker = new ParcelLocker(user1, lockedUntil)

        when:
        locker.prolong(prolongedAt)

        then:
        locker.lockUntil == prolongedLockedUntil
    }

    def "should throw exception upon prolongation when locker is not assigned to any user"() {
        given:
        def locker = ParcelLocker.empty()

        when:
        locker.prolong(anyTimestamp)

        then:
        thrown(IllegalStateException)
    }

    def "should throw exception when prolongation is done too early"() {
        given:
        def now = Instant.now()
        def lockUntil = now + Duration.ofMinutes(20) // more than 15 minutes
        def locker = new ParcelLocker(user1, lockUntil)

        when:
        locker.prolong(now)

        then:
        thrown(IllegalStateException)
    }

    def "should throw exception upon prolongation when locker expired"() {
        given:
        def now = Instant.now()
        def lockUntil = now - Duration.ofMinutes(10) // expired
        def locker = new ParcelLocker(user1, lockUntil)

        when:
        locker.prolong(now)

        then:
        thrown(IllegalStateException)
    }
}
