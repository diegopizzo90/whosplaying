package com.diegopizzo.network.interactor.fixture

import com.diegopizzo.network.model.FixtureDataModel
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

interface IFixtureInteractor {
    fun getFixturesByLeagueAndDate(
        leagueId: List<String>,
        from: LocalDate,
        to: LocalDate
    ): Flow<List<FixtureDataModel>?>

    suspend fun clearCache()
}