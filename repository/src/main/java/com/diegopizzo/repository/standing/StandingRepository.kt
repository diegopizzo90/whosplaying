package com.diegopizzo.repository.standing

import com.diegopizzo.database.creator.standing.StandingCreator
import com.diegopizzo.database.dao.StandingDao
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.interactor.standing.IStandingInteractor
import com.diegopizzo.network.model.StandingDataModel
import com.diegopizzo.repository.league.ILeagueRepository
import com.diegopizzo.repository.util.LeagueIdNullException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class StandingRepository(
    private val dao: StandingDao,
    private val interactor: IStandingInteractor,
    private val defaultDispatcher: CoroutineDispatcher,
    private val creator: StandingCreator,
    private val leagueRepository: ILeagueRepository
) : IStandingRepository {

    override suspend fun getStandingByLeague(leagueName: LeagueName): List<StandingDataModel> {
        return withContext(defaultDispatcher) {
            val leagueId = leagueRepository.getLeagueId(leagueName) ?: throw LeagueIdNullException()
            try {
                val standing = interactor.getStandingByLeagueId(leagueId)
                dao.deleteAllByLeagueId(leagueId.toLong())
                dao.insertStandings(*creator.fromDataModelListToEntities(standing, leagueId))
                standing
            } catch (e: Exception) {
                if (e is LeagueIdNullException) emptyList<StandingDataModel>()
                creator.fromEntitiesToDataModelList(dao.getStandingsByLeagueId(leagueId.toLong()))
            }
        }
    }
}