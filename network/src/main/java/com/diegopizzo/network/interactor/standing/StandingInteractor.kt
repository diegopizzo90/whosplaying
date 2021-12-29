package com.diegopizzo.network.interactor.standing

import com.diegopizzo.network.cache.standing.IStandingInteractorCache
import com.diegopizzo.network.creator.standing.StandingModelCreator
import com.diegopizzo.network.model.StandingDataModel
import org.threeten.bp.ZonedDateTime

class StandingInteractor(
    private val cache: IStandingInteractorCache,
    private val creator: StandingModelCreator
) : IStandingInteractor {

    override suspend fun getStandingByLeagueId(leagueId: String): List<StandingDataModel> {
        val model = cache.getStanding(ZonedDateTime.now().year.toString(), leagueId).body()
        return creator.fromModelToDataModel(model)
    }
}