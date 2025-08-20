package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;

public class ParcelLockerV2 {

    private User assignedTo;
    private Instant lockUntil;
    private int numberOfProlongs;

    public ParcelLockerV2(User assignedTo, Instant lockUntil, int numberOfProlongs) {
        this.assignedTo = assignedTo;
        this.lockUntil = lockUntil;
        this.numberOfProlongs = numberOfProlongs;
    }

    public static ParcelLockerV2 empty() {
        return new ParcelLockerV2(null, null, 0);
    }

    public void lockFor(User user, Instant now) {
        if (this.assignedTo != null) {
            throw new IllegalStateException("Parcel locker is already locked for user");
        }

        this.assignedTo = user;
        this.lockUntil = now.plus(Period.ofDays(1));
        this.numberOfProlongs = 0;
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
        if (this.numberOfProlongs >= 3) {
            throw new IllegalStateException("Parcel locker cannot be prolonged more than 3 times");
        }

        this.lockUntil = this.lockUntil.plus(Period.ofDays(1));
        this.numberOfProlongs++;
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
        this.numberOfProlongs = 0;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public Instant getLockUntil() {
        return lockUntil;
    }

    public int getNumberOfProlongs() {
        return numberOfProlongs;
    }
}
