package pl.cezarysanecki.unittestingrevisitedworkshop;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Stats {

    @Id
    Long id;

    UUID accountId;

    Long views;
    Long likes;

    public Stats() {
    }

    public Stats(Long id, Long likes, UUID accountId, Long views) {
        this.id = id;
        this.likes = likes;
        this.accountId = accountId;
        this.views = views;
    }
}
