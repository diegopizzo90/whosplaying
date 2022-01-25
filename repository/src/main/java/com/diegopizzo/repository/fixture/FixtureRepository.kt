package com.diegopizzo.repository.fixture

import com.diegopizzo.network.interactor.fixture.IFixtureInteractor
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.database.creator.fixture.FixtureCreator
import com.diegopizzo.database.dao.FixtureDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.*
import org.threeten.bp.LocalDate

internal class FixtureRepository(
    private val interactor: IFixtureInteractor,
    private val fixtureDao: FixtureDao,
    private val defaultDispatcher: CoroutineDispatcher,
    private val creator: FixtureCreator
) : IFixtureRepository {

    override fun getFixtures(
        leagueId: String, from: LocalDate, to: LocalDate
    ): Flow<List<FixtureDataModel>?> {
        return interactor.getFixturesByLeagueAndDate(leagueId, from, to).cancellable()
            .onEach { dataModelList ->
                currentCoroutineContext().ensureActive()
                dataModelList?.let {
                    fixtureDao.deleteAllByLeagueId(leagueId.toLong())
                    val entities = creator.toFixtureEntityArray(it, leagueId)
                    fixtureDao.insertFixture(*entities)
                }
            }.flowOn(defaultDispatcher)
            .catch {
                val entities = fixtureDao.getFixturesByLeagueId(leagueId.toLong())
                val dataModels = creator.toFixtureDataModels(entities)
                emit(dataModels)
            }
    }
}