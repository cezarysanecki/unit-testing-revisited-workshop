package pl.cezarysanecki.unittestingrevisitedworkshop;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Stats {

    @Id
    public Long id;

    public UUID accountId;

    public Long views;
    public Long likes;

    public Stats() {
    }

    public Stats(Long id, Long likes, UUID accountId, Long views) {
        this.id = id;
        this.likes = likes;
        this.accountId = accountId;
        this.views = views;
    }
}
