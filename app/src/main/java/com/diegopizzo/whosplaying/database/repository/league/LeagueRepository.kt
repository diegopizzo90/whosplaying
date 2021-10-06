package com.diegopizzo.whosplaying.database.repository.league

import com.diegopizzo.network.interactor.league.ILeagueInteractor
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.LeagueInfo
import com.diegopizzo.whosplaying.database.creator.league.LeagueCreator
import com.diegopizzo.whosplaying.database.dao.LeagueDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LeagueRepository(
    private val leagueDao: LeagueDao,
    private val interactor: ILeagueInteractor,
    private val creator: LeagueCreator,
    private val leagueList: List<LeagueName>
) : ILeagueRepository {

    override suspend fun downloadLeaguesInfo() {
        withContext(Dispatchers.IO) {
            leagueList.forEach {
                val leagueEntity = leagueDao.getLeagueByName(it.stringName)
                if (leagueEntity == null) {
                    val leagueInfo = interactor.getLeague(it)
                    saveLeague(leagueInfo)
                }
            }
        }
    }

    private fun saveLeague(leagueInfo: LeagueInfo?) {
        leagueInfo?.let { leagueDao.insertLeagues(creator.toLeagueEntity(it)) }
    }
}