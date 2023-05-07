package com.diegopizzo.network.interactor.league

import com.diegopizzo.network.interactor.league.CountryCode.*
import com.diegopizzo.network.interactor.league.LeagueType.CUP
import com.diegopizzo.network.interactor.league.LeagueType.LEAGUE
import com.diegopizzo.network.model.LeagueResponse
import com.diegopizzo.network.service.RetrofitApi

internal class LeagueInteractor(private val api: RetrofitApi) : ILeagueInteractor {
    override suspend fun getLeague(leagueName: LeagueName): LeagueResponse? {
        return api.getLeagueByCountry(leagueName.stringName, leagueName.alpha2Code.code)
            .body()?.response?.first()
    }
}

enum class LeagueType(val type: String) {
    LEAGUE("League"), CUP("Cup");

    companion object {
        fun fromValue(value: String): LeagueType? {
            return values().find { it.type == value }
        }
    }
}

enum class CountryCode(val code: String) {
    ITALY("IT"),
    FRANCE("FR"),
    GERMANY("DE"),
    SPAIN("ES"),
    ENGLAND("GB")
}

enum class LeagueName(val stringName: String, val alpha2Code: CountryCode, val type: LeagueType) {
    SERIE_A("Serie A", ITALY, LEAGUE),
    COPPA_ITALIA("Coppa Italia", ITALY, CUP),
    PREMIER_LEAGUE("Premier League", ENGLAND, LEAGUE),
    LIGUE_1("Ligue 1", FRANCE, LEAGUE),
    LA_LIGA("La Liga", SPAIN, LEAGUE),
    BUNDESLIGA("Bundesliga", GERMANY, LEAGUE)
}