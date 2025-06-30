package pl.cezarysanecki.unittestingrevisitedworkshop;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;

@Getter
public class ParcelLocker {

    private User assignedTo;
    private Instant lockUntil;
    private final Integer maximumProlongedAmount;
    private Integer prolongedAmount = 0;

    public ParcelLocker(User assignedTo, Instant lockUntil, Integer maximumProlongedAmount) {
        this.assignedTo = assignedTo;
        this.lockUntil = lockUntil;
        this.maximumProlongedAmount = maximumProlongedAmount;
    }

    public static ParcelLocker empty(Integer maximumProlongedAmount) {
        return new ParcelLocker(null, null, maximumProlongedAmount);
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
        if (prolongedAmount >= maximumProlongedAmount) {
            throw new IllegalStateException("Parcel locker cannot be prolonged more than 1 time");
        }

        this.lockUntil = this.lockUntil.plus(Period.ofDays(1));
        this.prolongedAmount += 1;
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


}
