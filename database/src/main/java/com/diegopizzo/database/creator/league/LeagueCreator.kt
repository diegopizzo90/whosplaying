package com.diegopizzo.database.creator.league

import com.diegopizzo.database.entity.LeagueEntity
import com.diegopizzo.network.interactor.league.LeagueType
import com.diegopizzo.network.model.LeagueResponse

class LeagueCreator {
    fun toLeagueEntity(leagueResponse: LeagueResponse): LeagueEntity {
        val league = leagueResponse.league
        val country = leagueResponse.country
        return LeagueEntity(
            league.id.toLong(),
            league.name,
            league.logo,
            country.name,
            country.code,
            LeagueType.fromValue(league.type) ?: LeagueType.LEAGUE
        )
    }
}