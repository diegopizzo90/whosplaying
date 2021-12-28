package com.diegopizzo.network.cache.event

import com.diegopizzo.network.cache.CacheConstant.EVENT_DURATION_SECONDS
import com.diegopizzo.network.model.EventModel
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
    private val ttlCache: Int = EVENT_DURATION_SECONDS
) : IEventInteractorCache {

    @OptIn(FlowPreview::class)
    private val store: Store<Long, Response<EventModel>> =
        StoreBuilder.from(fetcher = Fetcher.of { key: Long -> api.getEventByFixtureId(key) })
            .cachePolicy(
                MemoryPolicy.builder<Any, Any>()
                    .setExpireAfterWrite(Duration.seconds(ttlCache))
                    .build()
            ).build()


    override suspend fun getEventByFixtureId(fixtureId: Long): Response<EventModel> {
        return store.get(fixtureId)
    }
}