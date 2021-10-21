package com.diegopizzo.network.creator

import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.network.model.FixtureModel
import com.diegopizzo.network.model.ResponseFixture
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Response

class FixtureModelCreator {

    fun toFixturesDataModel(fixtureModel: Response<FixtureModel>): List<FixtureDataModel>? {
        val listSorted = fixtureModel.body()?.response?.map { it }
            ?.sortedBy {
                LocalDateTime.parse(
                    it.fixture.date,
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                )
            }
        return listSorted?.map {
            toFixtureDataModel(it)
        }
    }

    private fun toFixtureDataModel(responseFixture: ResponseFixture): FixtureDataModel {
        val fixture = responseFixture.fixture
        val home = responseFixture.teams.home
        val away = responseFixture.teams.away
        val goals = responseFixture.goals
        return FixtureDataModel(
            fixture.id,
            fixture.date,
            fixture.status.short,
            fixture.status.elapsed?.toString(),
            home.name,
            away.name,
            home.logo,
            away.logo,
            goals.home?.toString() ?: "",
            goals.away?.toString() ?: "",
        )
    }
}