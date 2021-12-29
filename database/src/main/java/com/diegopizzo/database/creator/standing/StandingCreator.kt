package com.diegopizzo.database.creator.standing

import com.diegopizzo.database.entity.StandingEntity
import com.diegopizzo.network.model.StandingDataModel

class StandingCreator {

    fun fromEntityToDataModel(entity: StandingEntity): StandingDataModel {
        entity.apply {
            return StandingDataModel(
                idTeam,
                nameTeam,
                logoTeam,
                rank,
                points,
                goalsDiff,
                form,
                played,
                win,
                draw,
                lose,
                scored,
                against
            )
        }
    }

    fun fromDataModelToEntity(dataModel: StandingDataModel, leagueId: Long): StandingEntity {
        dataModel.apply {
            return StandingEntity(
                idTeam,
                nameTeam,
                logoTeam,
                rank,
                points,
                goalsDiff,
                form,
                played,
                win,
                draw,
                lose,
                scored,
                against,
                leagueId
            )
        }
    }
}