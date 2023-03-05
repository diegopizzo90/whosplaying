package com.diegopizzo.network.creator.fixture

import com.diegopizzo.network.CommonConstant
import com.diegopizzo.network.Util
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.network.model.FixtureModel
import com.diegopizzo.network.model.ResponseFixture
import com.diegopizzo.network.model.StatusValue
import org.threeten.bp.ZoneId
import retrofit2.Response

class FixtureModelCreator {

    fun toFixturesDataModel(fixtureModel: List<Response<FixtureModel>>): List<FixtureDataModel> {
        return fixtureModel.flatMap { fixture ->
            fixture.body()?.response?.sortedBy { it.fixture.date }?.map {
                toFixtureDataModel(it)
            } ?: emptyList()
        }.toList()
    }

    private fun toFixtureDataModel(responseFixture: ResponseFixture): FixtureDataModel {
        val fixture = responseFixture.fixture
        val home = responseFixture.teams.home
        val away = responseFixture.teams.away
        val goals = responseFixture.goals
        val timeEvent = Util.convertUtcDateTimeToLocal(
            fixture.date,
            ZoneId.systemDefault(),
            CommonConstant.TIME_PATTERN
        )
        return FixtureDataModel(
            fixture.id,
            fixture.date,
            getFixtureStatus(fixture.status.short, fixture.status.elapsed?.toString(), timeEvent),
            fixture.status.elapsed?.toString(),
            home.name,
            away.name,
            home.logo,
            away.logo,
            goals.home?.toString() ?: "",
            goals.away?.toString() ?: "",
            isFixtureLive(fixture.status.short)
        )
    }

    private fun getFixtureStatus(statusShort: String, elapsed: String?, timeEvent: String): String {
        return when (statusShort) {
            StatusValue.FIRST_HALF.short, StatusValue.SECOND_HALF.short, StatusValue.EXTRA_TIME.short -> "$elapsed′"
            StatusValue.NOT_STARTED.short -> timeEvent
            else -> statusShort
        }
    }

    private fun isFixtureLive(statusShort: String): Boolean {
        return when (statusShort) {
            StatusValue.FIRST_HALF.short, StatusValue.SECOND_HALF.short, StatusValue.EXTRA_TIME.short -> true
            else -> false
        }
    }
}