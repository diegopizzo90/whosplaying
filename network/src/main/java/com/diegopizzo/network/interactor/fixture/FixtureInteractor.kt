package com.diegopizzo.network.interactor.fixture

import com.diegopizzo.network.cache.fixture.IFixtureInteractorCache
import com.diegopizzo.network.cache.fixture.config.DEFAULT_DURATION_MILLIS
import com.diegopizzo.network.creator.FixtureModelCreator
import com.diegopizzo.network.model.FixtureDataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class FixtureInteractor(
    private val cache: IFixtureInteractorCache,
    private val creator: FixtureModelCreator,
    private val refreshIntervalMs: Long = DEFAULT_DURATION_MILLIS
) : IFixtureInteractor {
    override fun getFixturesByLeagueAndDate(
        leagueId: String,
        from: LocalDate,
        to: LocalDate
    ): Flow<List<FixtureDataModel>?> {
        return flow {
            while (true) {
                val response = cache.getFixturesByLeagueIdAndByDate(
                    leagueId,
                    LocalDate.now().year.toString(),
                    from.format(DateTimeFormatter.ISO_DATE),
                    to.format(DateTimeFormatter.ISO_DATE)
                )
                val fixtures = creator.toFixturesDataModel(response)
                emit(fixtures)
                delay(refreshIntervalMs)
            }
        }
    }
}