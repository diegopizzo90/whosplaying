package com.diegopizzo.network.cache.fixture

import com.diegopizzo.network.cache.CacheConstant.DEFAULT_DURATION_MILLIS
import com.diegopizzo.network.model.FixtureModel
import com.diegopizzo.network.service.RetrofitApi
import com.dropbox.android.external.store4.*
import retrofit2.Response
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, ExperimentalStoreApi::class)
internal class FixtureInteractorCache(
    private val api: RetrofitApi,
    private val ttlCache: Long = DEFAULT_DURATION_MILLIS
) : IFixtureInteractorCache {

    private val store: Store<FixtureParameters, Response<FixtureModel>> =
        StoreBuilder.from(fetcher = Fetcher.of { key: FixtureParameters ->
            api.getFixturesByLeagueIdAndByDate(
                key.leagueId,
                key.season,
                key.from,
                key.to
            )
        }).cachePolicy(
            MemoryPolicy.builder<Any, Any>()
                .setExpireAfterWrite(Duration.milliseconds(ttlCache))
                .build()
        ).build()


    override suspend fun getFixturesByLeagueIdAndByDate(
        leagueId: String,
        season: String,
        from: String,
        to: String
    ): Response<FixtureModel> {
        return store.get(FixtureParameters(leagueId, season, from, to))
    }

    override suspend fun clearCache() {
        store.clearAll()
    }

    private data class FixtureParameters(
        val leagueId: String,
        val season: String,
        val from: String,
        val to: String
    )
}