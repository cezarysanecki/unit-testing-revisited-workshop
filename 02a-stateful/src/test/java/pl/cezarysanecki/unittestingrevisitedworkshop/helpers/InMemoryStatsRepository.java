package pl.cezarysanecki.unittestingrevisitedworkshop.helpers;

import pl.cezarysanecki.unittestingrevisitedworkshop.Stats;
import pl.cezarysanecki.unittestingrevisitedworkshop.StatsRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class InMemoryStatsRepository implements StatsRepository {

    private static final Map<Long, Stats> STORE = new ConcurrentHashMap<>();

    private final Supplier<Long> idGenerator;

    public InMemoryStatsRepository(Supplier<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Optional<Stats> findByAccountId(UUID accountId) {
        return STORE.values().stream()
                .filter(stats -> stats.accountId.equals(accountId))
                .findFirst();
    }

    @Override
    public Stats save(Stats entity) {
        if (entity.id == null) {
            entity.id = idGenerator.get();
        }
        STORE.put(entity.id, entity);
        return entity;
    }

    @Override
    public <S extends Stats> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Stats> findById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Stats> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Stats> findAllById(Iterable<Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Stats entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends Stats> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
