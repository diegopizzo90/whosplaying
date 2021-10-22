package com.diegopizzo.whosplaying.repository.league

import com.diegopizzo.network.interactor.league.ILeagueInteractor
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.LeagueInfo
import com.diegopizzo.database.creator.league.LeagueCreator
import com.diegopizzo.database.dao.LeagueDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LeagueRepository(
    private val leagueDao: LeagueDao,
    private val interactor: ILeagueInteractor,
    private val creator: LeagueCreator,
    private val leagueList: List<LeagueName>,
    private val defaultDispatcher: CoroutineDispatcher,
) : ILeagueRepository {

    override suspend fun downloadLeaguesInfo() {
        withContext(defaultDispatcher) {
            try {
                leagueList.forEach {
                    val leagueEntity = leagueDao.getLeagueByName(it.stringName)
                    if (leagueEntity == null) {
                        val leagueInfo = interactor.getLeague(it)
                        saveLeague(leagueInfo)
                    }
                }
            } catch (e: Exception) {
                return@withContext
            }
        }
    }

    private fun saveLeague(leagueInfo: LeagueInfo?) {
        leagueInfo?.let { leagueDao.insertLeagues(creator.toLeagueEntity(it)) }
    }

    override suspend fun getLeagueId(leagueName: LeagueName): Long? {
        return withContext(defaultDispatcher) {
            leagueDao.getLeagueByName(leagueName.stringName)?.leagueId
        }
    }
}