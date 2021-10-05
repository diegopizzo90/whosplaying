package com.diegopizzo.network.interactor.fixture

import com.diegopizzo.network.creator.FixtureModelCreator
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.network.service.RetrofitApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class FixtureInteractor(
    private val api: RetrofitApi,
    private val creator: FixtureModelCreator,
    private val refreshIntervalMs: Long = 5000 //seconds
) : IFixtureInteractor {
    override fun getFixturesByLeagueAndDate(
        leagueId: String,
        from: LocalDate,
        to: LocalDate
    ): Flow<List<FixtureDataModel>?> {
        return flow {
            while (true) {
                val response = api.getFixturesByLeagueIdAndByDate(
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