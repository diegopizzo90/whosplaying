package com.diegopizzo.network.cache.standing

import com.diegopizzo.network.cache.CacheConstant.STANDING_DURATION_MINUTES
import com.diegopizzo.network.model.StandingModel
import com.diegopizzo.network.service.RetrofitApi
import com.dropbox.android.external.store4.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Response
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
internal class StandingInteractorCache(
    private val api: RetrofitApi,
    private val ttlCache: Int = STANDING_DURATION_MINUTES
) : IStandingInteractorCache {

    private val store: Store<StandingParameters, Response<StandingModel>> = StoreBuilder.from(
        fetcher = Fetcher.of { key: StandingParameters ->
            api.getStanding(
                key.season,
                key.leagueId
            )
        }
    ).cachePolicy(
        MemoryPolicy.builder<Any, Any>()
            .setExpireAfterWrite(Duration.minutes(ttlCache))
            .build()
    ).build()

    override suspend fun getStanding(
        season: String,
        leagueId: String
    ): Response<StandingModel> {
        return store.get(StandingParameters(season, leagueId))
    }

    private data class StandingParameters(
        val leagueId: String,
        val season: String
    )
}