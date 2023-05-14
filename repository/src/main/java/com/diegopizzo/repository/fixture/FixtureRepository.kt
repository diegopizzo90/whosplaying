package com.diegopizzo.repository.fixture

import com.diegopizzo.database.creator.fixture.FixtureCreator
import com.diegopizzo.database.dao.FixtureDao
import com.diegopizzo.network.Util.toEndZoneDateTime
import com.diegopizzo.network.Util.toStartZoneDateTime
import com.diegopizzo.network.interactor.fixture.IFixtureInteractor
import com.diegopizzo.network.model.FixtureDataModel
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
        leagueId: List<String>,
        from: LocalDate,
        to: LocalDate
    ): Flow<List<FixtureDataModel>?> {
        return interactor.getFixturesByLeagueAndDate(leagueId, from, to).cancellable()
            .onEach { dataModelList ->
                currentCoroutineContext().ensureActive()
                dataModelList?.let {
                    val entities = creator.toFixtureEntityArray(it, leagueId)
                    fixtureDao.insertFixture(*entities)
                }
            }
            .catch {
                onError(leagueId, from, to)
            }
            .flowOn(defaultDispatcher)
    }

    private suspend fun FlowCollector<List<FixtureDataModel>>.onError(
        leagueId: List<String>,
        from: LocalDate,
        to: LocalDate
    ) {
        interactor.clearCache()
        val entities = leagueId.map { id ->
            fixtureDao.getFixturesByLeagueId(
                id.toLong(),
                from.toStartZoneDateTime(),
                to.toEndZoneDateTime()
            )
        }

        val dataModels = creator.toFixtureDataModels(entities.flatMap { it.orEmpty() })
        emit(dataModels)
    }
}