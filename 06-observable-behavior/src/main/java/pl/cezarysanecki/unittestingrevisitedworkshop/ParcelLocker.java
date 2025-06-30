package pl.cezarysanecki.unittestingrevisitedworkshop;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;

@Getter
public class ParcelLocker {

    private static final Integer MAX_PROLONGED_COUNT = 3;

    private User assignedTo;
    private Instant lockUntil;
    private Integer prolongedCount = 0;
    private final Integer maxProlongedCount;

    public ParcelLocker(User assignedTo, Instant lockUntil, Integer prolongedCount, Integer maxProlongedCount) {
        this.assignedTo = assignedTo;
        this.lockUntil = lockUntil;
        this.prolongedCount = prolongedCount;
        this.maxProlongedCount = maxProlongedCount;
    }

    public ParcelLocker(User assignedTo, Instant lockUntil) {
        this.assignedTo = assignedTo;
        this.lockUntil = lockUntil;
        this.prolongedCount = 0;
        this.maxProlongedCount = MAX_PROLONGED_COUNT;
    }

    public static ParcelLocker empty() {
        return new ParcelLocker(null, null);
    }

    public void lockFor(User user, Instant now) {
        if (this.assignedTo != null) {
            throw new IllegalStateException("Parcel locker is already locked for user");
        }

        this.assignedTo = user;
        this.lockUntil = now.plus(Period.ofDays(1));
    }

    public void prolong(Instant now) {
        if (this.assignedTo == null) {
            throw new IllegalStateException("Parcel locker is not locked");
        }
        if (now.isAfter(this.lockUntil) || now.plus(Duration.ofMinutes(15)).isBefore(this.lockUntil)) {
            throw new IllegalStateException("It is too early to prolong parcel locker");
        }
        if (this.prolongedCount >= this.maxProlongedCount) {
            throw new IllegalStateException("Parcel locker cannot be prolonged more than  " + this.maxProlongedCount + " times");
        }

        this.lockUntil = this.lockUntil.plus(Period.ofDays(1));
        this.prolongedCount++;
    }

    public void open(User user, Instant now) {
        if (this.assignedTo == null || !this.assignedTo.equals(user)) {
            throw new IllegalStateException("Parcel locker is not locked for this user");
        }
        if (now.isAfter(this.lockUntil)) {
            throw new IllegalStateException("Parcel locker is locked until " + this.lockUntil);
        }
        release();
    }

    public void release() {
        this.assignedTo = null;
        this.lockUntil = null;
        this.prolongedCount = 0;
    }
}
