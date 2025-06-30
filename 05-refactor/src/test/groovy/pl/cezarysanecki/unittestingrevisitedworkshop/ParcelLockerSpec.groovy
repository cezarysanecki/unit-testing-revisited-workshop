package pl.cezarysanecki.unittestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop._2.ParcelLocker
import pl.cezarysanecki.unittestingrevisitedworkshop._2.User
import spock.lang.Specification

import java.time.Duration
import java.time.Instant
import java.time.Period

class ParcelLockerSpec extends Specification {

    def "should prolong parcel locker"() {
        given: "a parcel locker"
        def user = new User(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
        def now = Instant.now()
        def locker = new ParcelLocker(user, now)

        when:
        locker.prolong(now.minus(Duration.ofMinutes(10)))

        then:
        locker.lockUntil == now.plus(Period.ofDays(1))
    }
}
