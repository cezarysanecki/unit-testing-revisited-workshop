package pl.cezarysanecki.unittestingrevisitedworkshop.parcel;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;

public class ParcelLocker {

    private User assignedTo;
    private Instant lockUntil;

    public ParcelLocker(User assignedTo, Instant lockUntil) {
        this.assignedTo = assignedTo;
        this.lockUntil = lockUntil;
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
        if (now.isAfter(this.lockUntil)) {
            throw new IllegalStateException("It is too late to prolong parcel locker");
        }
        if (now.plus(Duration.ofMinutes(15)).isBefore(this.lockUntil)) {
            throw new IllegalStateException("It is too early to prolong parcel locker");
        }

        this.lockUntil = this.lockUntil.plus(Period.ofDays(1));
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
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public Instant getLockUntil() {
        return lockUntil;
    }
}
