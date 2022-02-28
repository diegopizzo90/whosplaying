package com.diegopizzo.network.cache.event

import com.diegopizzo.network.cache.CacheConstant.EVENT_DURATION_MILLIS
import com.diegopizzo.network.model.EventModel
import com.diegopizzo.network.model.LineupsModel
import com.diegopizzo.network.model.StatisticsModel
import com.diegopizzo.network.service.RetrofitApi
import com.dropbox.android.external.store4.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import retrofit2.Response
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
internal class EventInteractorCache(
    private val api: RetrofitApi,
    private val ttlCache: Long = EVENT_DURATION_MILLIS
) : IEventInteractorCache {

    @OptIn(FlowPreview::class)
    private val eventStore: Store<Long, Response<EventModel>> =
        StoreBuilder.from(fetcher = Fetcher.of { key: Long -> api.getEventByFixtureId(key) })
            .cachePolicy(
                MemoryPolicy.builder<Any, Any>()
                    .setExpireAfterWrite(Duration.milliseconds(ttlCache))
                    .build()
            ).build()

    @OptIn(FlowPreview::class)
    private val statisticsStore: Store<Long, Response<StatisticsModel>> =
        StoreBuilder.from(fetcher = Fetcher.of { key: Long -> api.getStatistics(key) })
            .cachePolicy(
                MemoryPolicy.builder<Any, Any>()
                    .setExpireAfterWrite(Duration.milliseconds(ttlCache))
                    .build()
            ).build()

    @OptIn(FlowPreview::class)
    private val lineupsStore: Store<Long, Response<LineupsModel>> =
        StoreBuilder.from(fetcher = Fetcher.of { key: Long -> api.getLineups(key) })
            .cachePolicy(
                MemoryPolicy.builder<Any, Any>()
                    .setExpireAfterWrite(Duration.milliseconds(ttlCache))
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
}