package com.diegopizzo.database.creator.standings

import com.diegopizzo.database.entity.StandingsEntity
import com.diegopizzo.network.model.StandingsDataModel

class StandingsCreator {

    private fun fromEntityToDataModel(entity: StandingsEntity): StandingsDataModel {
        entity.apply {
            return StandingsDataModel(
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
        dataModel: StandingsDataModel,
        leagueId: String
    ): StandingsEntity {
        dataModel.apply {
            return StandingsEntity(
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
        dataModels: List<StandingsDataModel>,
        leagueId: String
    ): Array<StandingsEntity> {
        return dataModels.map {
            fromDataModelToEntity(it, leagueId)
        }.toTypedArray()
    }

    fun fromEntitiesToDataModelList(entities: List<StandingsEntity>?): List<StandingsDataModel> {
        return entities?.map {
            fromEntityToDataModel(it)
        } ?: emptyList()
    }
}