package com.diegopizzo.network.cache.event

import com.diegopizzo.network.cache.CacheConstant.EVENT_DURATION_MILLIS
import com.diegopizzo.network.model.EventModel
import com.diegopizzo.network.model.HeadToHeadModel
import com.diegopizzo.network.model.LineupsModel
import com.diegopizzo.network.model.StatisticsModel
import com.diegopizzo.network.service.RetrofitApi
import com.dropbox.android.external.store4.*
import retrofit2.Response
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, ExperimentalStoreApi::class)
internal class EventInteractorCache(
    private val api: RetrofitApi,
    private val ttlCache: Long = EVENT_DURATION_MILLIS
) : IEventInteractorCache {

    private val eventStore: Store<Long, Response<EventModel>> =
        StoreBuilder.from(fetcher = Fetcher.of { key: Long -> api.getEventByFixtureId(key) })
            .cachePolicy(
                MemoryPolicy.builder<Any, Any>()
                    .setExpireAfterWrite(ttlCache.milliseconds)
                    .build()
            ).build()

    private val statisticsStore: Store<Long, Response<StatisticsModel>> =
        StoreBuilder.from(fetcher = Fetcher.of { key: Long -> api.getStatistics(key) })
            .cachePolicy(
                MemoryPolicy.builder<Any, Any>()
                    .setExpireAfterWrite(ttlCache.milliseconds)
                    .build()
            ).build()

    private val lineupsStore: Store<Long, Response<LineupsModel>> =
        StoreBuilder.from(fetcher = Fetcher.of { key: Long -> api.getLineups(key) })
            .cachePolicy(
                MemoryPolicy.builder<Any, Any>()
                    .setExpireAfterWrite(ttlCache.milliseconds)
                    .build()
            ).build()

    private val headToHeadStore: Store<String, Response<HeadToHeadModel>> =
        StoreBuilder.from(fetcher = Fetcher.of { key: String -> api.getHeadToHead(key) })
            .cachePolicy(
                MemoryPolicy.builder<Any, Any>()
                    .setExpireAfterWrite(ttlCache.milliseconds)
                    .build()
            ).build()

    override suspend fun getEventByFixtureId(fixtureId: Long): Response<EventModel> {
        return eventStore.get(fixtureId)
    }

    override suspend fun getStatistics(fixtureId: Long): Response<StatisticsModel> {
        return statisticsStore.get(fixtureId)
    }

    override suspend fun getLineups(fixtureId: Long): Response<LineupsModel> {
        return lineupsStore.get(fixtureId)
    }

    override suspend fun getHeadToHead(fixtureIds: String): Response<HeadToHeadModel> {
        return headToHeadStore.get(fixtureIds)
    }

    override suspend fun clearCache() {
        eventStore.clearAll()
        statisticsStore.clearAll()
        lineupsStore.clearAll()
        headToHeadStore.clearAll()
    }
}