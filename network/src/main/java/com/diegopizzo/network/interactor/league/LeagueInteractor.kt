package com.diegopizzo.network.interactor.league

import com.diegopizzo.network.model.LeagueInfo
import com.diegopizzo.network.service.RetrofitApi

class LeagueInteractor(private val api: RetrofitApi) : ILeagueInteractor {
    override suspend fun getLeague(leagueName: LeagueName): LeagueInfo? {
        return api.getLeagueByCountry(leagueName.stringName, leagueName.alpha2Code)
            .body()?.response?.first()?.league
    }
}

enum class LeagueName(val stringName: String, val alpha2Code: String) {
    SERIE_A("Serie A", "IT"),
    PREMIER_LEAGUE("Premier League", "GB"),
    LIGUE_1("Ligue 1", "FR"),
    LA_LIGA("La Liga", "ES"),
    BUNDESLIGA("Bundesliga 1", "DE")
}