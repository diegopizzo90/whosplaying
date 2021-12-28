package com.diegopizzo.repository.fixture

import com.diegopizzo.network.model.FixtureDataModel
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

interface IFixtureRepository {
    fun getFixtures(leagueId: String, from: LocalDate, to: LocalDate): Flow<List<FixtureDataModel>?>
}