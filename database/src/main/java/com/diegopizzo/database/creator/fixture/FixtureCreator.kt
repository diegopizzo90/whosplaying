package com.diegopizzo.database.creator.fixture

import com.diegopizzo.database.entity.FixtureEntity
import com.diegopizzo.network.Util.dateTimeFormatter
import com.diegopizzo.network.model.FixtureDataModel
import org.threeten.bp.ZonedDateTime

class FixtureCreator {
    private fun toFixtureEntity(model: FixtureDataModel, leagueId: Long): FixtureEntity {
        return FixtureEntity(
            model.fixtureId,
            dateTimeEvent = ZonedDateTime.parse(model.dateTimeEventUtc, dateTimeFormatter),
            status = model.status,
            elapsed = model.elapsed,
            homeTeam = model.homeTeam,
            awayTeam = model.awayTeam,
            logoHomeTeam = model.logoHomeTeam,
            logoAwayTeam = model.logoAwayTeam,
            goalHomeTeam = model.goalHomeTeam,
            goalAwayTeam = model.goalAwayTeam,
            fixtureLeagueId = leagueId
        )
    }

    fun toFixtureEntityArray(
        models: List<FixtureDataModel>,
        leagueId: List<String>
    ): Array<FixtureEntity> {
        return leagueId.flatMap { id ->
            models.map {
                toFixtureEntity(it, id.toLong())
            }
        }.toTypedArray()
    }

    private fun toFixtureDataModel(entity: FixtureEntity): FixtureDataModel {
        return FixtureDataModel(
            entity.fixtureId,
            entity.dateTimeEvent.format(dateTimeFormatter),
            entity.status,
            entity.elapsed,
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