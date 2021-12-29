package com.diegopizzo.network.interactor.standing

import com.diegopizzo.network.model.StandingDataModel

interface IStandingInteractor {
    suspend fun getStandingByLeagueId(leagueId: String): List<StandingDataModel>
}
