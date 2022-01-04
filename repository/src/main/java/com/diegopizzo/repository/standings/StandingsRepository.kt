package com.diegopizzo.repository.standings

import com.diegopizzo.database.creator.standings.StandingsCreator
import com.diegopizzo.database.dao.StandingsDao
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.interactor.standings.IStandingsInteractor
import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.repository.league.ILeagueRepository
import com.diegopizzo.repository.util.LeagueIdNullException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class StandingsRepository(
    private val dao: StandingsDao,
    private val interactor: IStandingsInteractor,
    private val defaultDispatcher: CoroutineDispatcher,
    private val creator: StandingsCreator,
    private val leagueRepository: ILeagueRepository
) : IStandingsRepository {

    override suspend fun getStandingsByLeague(leagueName: LeagueName): List<StandingsDataModel> {
        return withContext(defaultDispatcher) {
            val leagueId = leagueRepository.getLeagueId(leagueName) ?: throw LeagueIdNullException()
            try {
                val standings = interactor.getStandingsByLeagueId(leagueId)
                dao.deleteAllByLeagueId(leagueId.toLong())
                dao.insertStandings(*creator.fromDataModelListToEntities(standings, leagueId))
                standings
            } catch (e: Exception) {
                if (e is LeagueIdNullException) emptyList<StandingsDataModel>()
                creator.fromEntitiesToDataModelList(dao.getStandingsByLeagueId(leagueId.toLong()))
            }
        }
    }
}