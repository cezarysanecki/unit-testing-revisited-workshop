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

    public Stats(UUID accountId) {
        this.accountId = accountId;
    }

    public Stats(Long id, UUID accountId, Long views, Long likes) {
        this.id = id;
        this.accountId = accountId;
        this.views = views;
        this.likes = likes;
    }
}
