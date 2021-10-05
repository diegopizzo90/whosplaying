package com.diegopizzo.whosplaying.database.repository.fixture

import com.diegopizzo.network.interactor.fixture.IFixtureInteractor
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.whosplaying.database.creator.fixture.FixtureCreator
import com.diegopizzo.whosplaying.database.dao.FixtureDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import org.threeten.bp.LocalDate

class FixtureRepository(
    private val interactor: IFixtureInteractor,
    private val fixtureDao: FixtureDao,
    private val defaultDispatcher: CoroutineDispatcher,
    private val creator: FixtureCreator
) : IFixtureRepository {

    override fun getFixtures(
        leagueId: String, from: LocalDate, to: LocalDate
    ): Flow<List<FixtureDataModel>?> {
        return interactor.getFixturesByLeagueAndDate(leagueId, from, to)
            .onEach { dataModelList ->
                dataModelList?.let {
                    val entities = creator.toFixtureEntityArray(it)
                    fixtureDao.insertFixture(*entities)
                }
            }.flowOn(defaultDispatcher)
            .catch {
                val entities = fixtureDao.getAll()
                val dataModels = creator.toFixtureDataModels(entities)
                emit(dataModels)
            }
    }
}