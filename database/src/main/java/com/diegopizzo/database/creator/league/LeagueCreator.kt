package com.diegopizzo.database.creator.league

import com.diegopizzo.network.model.LeagueInfo
import com.diegopizzo.database.entity.LeagueEntity

class LeagueCreator {
    fun toLeagueEntity(leagueInfo: LeagueInfo): LeagueEntity {
        return LeagueEntity(leagueInfo.id.toLong(), leagueInfo.name, leagueInfo.logo)
    }

    fun toLeagueInfo(leagueEntity: LeagueEntity): LeagueInfo {
        return LeagueInfo(leagueEntity.leagueId.toInt(), leagueEntity.name, leagueEntity.logo)
    }
}