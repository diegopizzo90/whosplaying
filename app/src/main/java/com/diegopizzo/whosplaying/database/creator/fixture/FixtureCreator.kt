package com.diegopizzo.whosplaying.database.creator.fixture

import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.whosplaying.database.entity.FixtureEntity

class FixtureCreator {
    private fun toFixtureEntity(model: FixtureDataModel): FixtureEntity {
        return FixtureEntity(
            dateEvent = model.dateEvent,
            timeEvent = model.timeEvent,
            status = model.status,
            homeTeam = model.homeTeam,
            awayTeam = model.awayTeam,
            logoHomeTeam = model.logoHomeTeam,
            logoAwayTeam = model.logoAwayTeam,
            goalHomeTeam = model.goalHomeTeam,
            goalAwayTeam = model.goalAwayTeam
        )
    }

    fun toFixtureEntityArray(models: List<FixtureDataModel>): Array<FixtureEntity> {
        return models.map {
            toFixtureEntity(it)
        }.toTypedArray()
    }

    private fun toFixtureDataModel(entity: FixtureEntity): FixtureDataModel {
        return FixtureDataModel(
            entity.dateEvent,
            entity.timeEvent,
            entity.status,
            entity.homeTeam,
            entity.awayTeam,
            entity.logoHomeTeam,
            entity.logoAwayTeam,
            entity.goalHomeTeam,
            entity.goalAwayTeam
        )
    }

    fun toFixtureDataModels(entities: List<FixtureEntity>?): List<FixtureDataModel> {
        return entities?.map {
            toFixtureDataModel(it)
        } ?: emptyList()
    }
}