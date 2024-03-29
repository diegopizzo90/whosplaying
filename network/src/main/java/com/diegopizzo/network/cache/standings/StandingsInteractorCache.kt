package com.diegopizzo.network.cache.standings

import com.diegopizzo.network.cache.CacheConstant.STANDINGS_DURATION_MINUTES
import com.diegopizzo.network.model.StandingsModel
import com.diegopizzo.network.service.RetrofitApi
import com.dropbox.android.external.store4.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Response
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, ExperimentalStoreApi::class)
internal class StandingsInteractorCache(
    private val api: RetrofitApi,
    private val ttlCache: Int = STANDINGS_DURATION_MINUTES
) : IStandingsInteractorCache {

    private val store: Store<StandingsParameters, Response<StandingsModel>> = StoreBuilder.from(
        fetcher = Fetcher.of { key: StandingsParameters ->
            api.getStandings(
                key.season,
                key.leagueId
            )
        }
    ).cachePolicy(
        MemoryPolicy.builder<Any, Any>()
            .setExpireAfterWrite(ttlCache.minutes)
            .build()
    ).build()

    override suspend fun getStandings(
        season: String,
        leagueId: String
    ): Response<StandingsModel> {
        return store.get(StandingsParameters(season, leagueId))
    }

    override suspend fun clearCache() {
        store.clearAll()
    }

    private data class StandingsParameters(
        val season: String,
        val leagueId: String
    )
}