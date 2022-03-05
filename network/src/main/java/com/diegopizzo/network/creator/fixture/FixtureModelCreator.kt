package com.diegopizzo.network.creator.fixture

import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.network.model.FixtureModel
import com.diegopizzo.network.model.ResponseFixture
import retrofit2.Response

class FixtureModelCreator {

    fun toFixturesDataModel(fixtureModel: Response<FixtureModel>): List<FixtureDataModel>? {
        return fixtureModel.body()?.response?.sortedBy { it.fixture.date }?.map {
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