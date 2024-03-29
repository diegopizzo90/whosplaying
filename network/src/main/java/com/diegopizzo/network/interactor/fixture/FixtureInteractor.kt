package com.diegopizzo.network.interactor.fixture

import com.diegopizzo.network.CommonConstant.SEASON
import com.diegopizzo.network.cache.CacheConstant.DEFAULT_DURATION_MILLIS
import com.diegopizzo.network.cache.fixture.IFixtureInteractorCache
import com.diegopizzo.network.creator.fixture.FixtureModelCreator
import com.diegopizzo.network.model.FixtureDataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

internal class FixtureInteractor(
    private val cache: IFixtureInteractorCache,
    private val creator: FixtureModelCreator,
    private val refreshIntervalMs: Long = DEFAULT_DURATION_MILLIS
) : IFixtureInteractor {
    override fun getFixturesByLeagueAndDate(
        leagueId: List<String>,
        from: LocalDate,
        to: LocalDate
    ): Flow<List<FixtureDataModel>?> {
        return flow {
            while (true) {
                val response = leagueId.map { id ->
                    cache.getFixturesByLeagueIdAndByDate(
                        id,
                        SEASON,
                        from.format(DateTimeFormatter.ISO_DATE),
                        to.format(DateTimeFormatter.ISO_DATE)
                    )
                }
                val fixtures = creator.toFixturesDataModel(response)
                emit(fixtures)
                delay(refreshIntervalMs)
            }
        }
    }

    override suspend fun clearCache() {
        cache.clearCache()
    }
}