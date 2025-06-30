package pl.cezarysanecki.unittestingrevisitedworkshop

import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryEventPublisher
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryFirstExternalSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemorySecondExternalSystem
import pl.cezarysanecki.unittestingrevisitedworkshop.helpers.InMemoryStatsRepository

interface StatsRepositoryFixture {
    FirstExternalSystem firstExternalSystem = new InMemoryFirstExternalSystem()
    SecondExternalSystem secondExternalSystem = new InMemorySecondExternalSystem()
    EventPublisher eventPublisher = new InMemoryEventPublisher()

    StatsRepository statsRepository = new InMemoryStatsRepository(() -> System.currentTimeMillis())
    StatsDownloader statsDownloader = new StatsDownloader(firstExternalSystem, secondExternalSystem, eventPublisher)
}
