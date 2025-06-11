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

}
