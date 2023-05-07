package com.diegopizzo.database.creator.league

import com.diegopizzo.database.entity.LeagueEntity
import com.diegopizzo.network.interactor.league.LeagueType
import com.diegopizzo.network.model.LeagueResponse
import org.threeten.bp.ZonedDateTime

class LeagueCreator {
    fun toLeagueEntity(leagueResponse: LeagueResponse, lastUpdate: ZonedDateTime): LeagueEntity {
        val league = leagueResponse.league
        val country = leagueResponse.country
        return LeagueEntity(
            leagueId = league.id.toLong(),
            name = league.name,
            logo = league.logo,
            countryName = country.name,
            countryCode = country.code,
            leagueType = LeagueType.fromValue(league.type) ?: LeagueType.LEAGUE,
            lastUpdate = lastUpdate
        )
    }
}