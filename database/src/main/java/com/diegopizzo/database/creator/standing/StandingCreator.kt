package com.diegopizzo.database.creator.standing

import com.diegopizzo.database.entity.StandingEntity
import com.diegopizzo.network.model.StandingDataModel

class StandingCreator {

    private fun fromEntityToDataModel(entity: StandingEntity): StandingDataModel {
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

    private fun fromDataModelToEntity(
        dataModel: StandingDataModel,
        leagueId: String
    ): StandingEntity {
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
                leagueId.toLong()
            )
        }
    }


    fun fromDataModelListToEntities(
        dataModels: List<StandingDataModel>,
        leagueId: String
    ): Array<StandingEntity> {
        return dataModels.map {
            fromDataModelToEntity(it, leagueId)
        }.toTypedArray()
    }

    fun fromEntitiesToDataModelList(entities: List<StandingEntity>?): List<StandingDataModel> {
        return entities?.map {
            fromEntityToDataModel(it)
        } ?: emptyList()
    }
}