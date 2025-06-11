package pl.cezarysanecki.unittestingrevisitedworkshop;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatsRepository extends CrudRepository<Stats, String> {

    Optional<Stats> findByAccountId(UUID accountId);

}
