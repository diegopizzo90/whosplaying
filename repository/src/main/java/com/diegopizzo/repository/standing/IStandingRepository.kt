package com.diegopizzo.repository.standing

import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.StandingDataModel

interface IStandingRepository {
    suspend fun getStandingByLeague(leagueName: LeagueName): List<StandingDataModel>
}