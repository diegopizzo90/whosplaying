package com.diegopizzo.network.creator

import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.network.model.FixtureModel
import com.diegopizzo.network.model.ResponseFixture
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Response

class FixtureModelCreator {

    fun toFixturesDataModel(fixtureModel: Response<FixtureModel>): List<FixtureDataModel>? {
        return fixtureModel.body()?.response?.map {
            toFixtureDataModel(it)
        }
    }

    private fun toFixtureDataModel(responseFixture: ResponseFixture): FixtureDataModel {
        val fixture = responseFixture.fixture
        val home = responseFixture.teams.home
        val away = responseFixture.teams.away
        val goals = responseFixture.goals
        return FixtureDataModel(
            convertUtcDateTimeToLocalDate(fixture.date),
            convertUtcDateTimeToLocalTime(fixture.date),
            fixture.status.elapsed?.toString() ?: "",
            home.name,
            away.name,
            home.logo,
            away.logo,
            goals.home?.toString() ?: "",
            goals.away?.toString() ?: "",
        )
    }

    fun convertUtcDateTimeToLocalDate(utcDate: String): String {
        return ZonedDateTime.parse(utcDate, DateTimeFormatter.ISO_DATE_TIME)
            .withZoneSameInstant(ZoneId.systemDefault())
            .format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
    }

    fun convertUtcDateTimeToLocalTime(utcDate: String): String {
        return ZonedDateTime.parse(utcDate, DateTimeFormatter.ISO_DATE_TIME)
            .withZoneSameInstant(ZoneId.systemDefault())
            .format(
                DateTimeFormatter.ofPattern("HH:mm")
            )
    }
}