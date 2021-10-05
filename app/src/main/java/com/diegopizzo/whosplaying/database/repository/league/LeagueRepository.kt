package com.diegopizzo.whosplaying.database.repository.league

import com.diegopizzo.network.interactor.league.ILeagueInteractor
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.LeagueInfo
import com.diegopizzo.whosplaying.database.creator.league.LeagueCreator
import com.diegopizzo.whosplaying.database.dao.LeagueDao

class LeagueRepository(
    private val leagueDao: LeagueDao,
    private val interactor: ILeagueInteractor,
    private val creator: LeagueCreator
) : ILeagueRepository {

    override suspend fun getLeagueByName(leagueName: LeagueName): LeagueInfo? {
        val leagueEntity = leagueDao.getLeagueByName(leagueName.stringName)
        return if (leagueEntity == null) {
            val leagueInfo = interactor.getLeague(leagueName)
            saveLeague(leagueInfo)
            leagueInfo
        } else {
            creator.toLeagueInfo(leagueEntity)
        }
    }

    private fun saveLeague(leagueInfo: LeagueInfo?) {
        leagueInfo?.let { leagueDao.insertUsers(creator.toLeagueEntity(it)) }
    }
}