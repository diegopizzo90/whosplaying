package com.diegopizzo.repository.league

import com.diegopizzo.network.interactor.league.CountryCode
import com.diegopizzo.network.interactor.league.LeagueName

interface ILeagueRepository {
    suspend fun downloadLeaguesInfo()
    suspend fun getLeagueId(leagueName: LeagueName): String?
    suspend fun getLeagueIdsByCountry(countryCode: CountryCode): List<String>?
}