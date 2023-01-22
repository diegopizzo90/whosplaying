package com.diegopizzo.repository.league

import com.diegopizzo.database.creator.league.LeagueCreator
import com.diegopizzo.database.dao.LeagueDao
import com.diegopizzo.network.interactor.league.CountryCode
import com.diegopizzo.network.interactor.league.ILeagueInteractor
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.LeagueResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class LeagueRepository(
    private val leagueDao: LeagueDao,
    private val interactor: ILeagueInteractor,
    private val creator: LeagueCreator,
    private val leagueList: List<LeagueName>,
    private val defaultDispatcher: CoroutineDispatcher,
) : ILeagueRepository {

    override suspend fun downloadLeaguesInfo() {
        withContext(defaultDispatcher) {
            try {
                val leaguesSize = leagueList.size
                val leagueEntities = leagueDao.getAll()
                if (leagueEntities?.size == 0 || leagueEntities?.size != leaguesSize) {
                    leagueList.forEach {
                        val leagueInfo = interactor.getLeague(it)
                        saveLeague(leagueInfo)
                    }
                }
            } catch (e: Exception) {
                return@withContext
            }
        }
    }

    private fun saveLeague(leagueInfo: LeagueResponse?) {
        leagueInfo?.let { leagueDao.insertLeagues(creator.toLeagueEntity(it)) }
    }

    override suspend fun getLeagueId(leagueName: LeagueName): String? {
        return withContext(defaultDispatcher) {
            leagueDao.getLeagueByName(leagueName.stringName)?.leagueId?.toString()
        }
    }

    override suspend fun getLeagueIdsByCountry(countryCode: CountryCode): List<String>? {
        return withContext(defaultDispatcher) {
            leagueDao.getLeaguesByCountry(countryCode.code)
                ?.map { it.leagueId.toString() }
        }
    }
}