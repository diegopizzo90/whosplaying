package com.diegopizzo.network.interactor.standings

import com.diegopizzo.network.cache.standings.IStandingsInteractorCache
import com.diegopizzo.network.creator.standings.StandingsModelCreator
import com.diegopizzo.network.model.StandingsDataModel
import org.threeten.bp.ZonedDateTime

class StandingsInteractor(
    private val cache: IStandingsInteractorCache,
    private val creator: StandingsModelCreator
) : IStandingsInteractor {

    override suspend fun getStandingsByLeagueId(leagueId: String): List<StandingsDataModel> {
        val model = cache.getStandings(ZonedDateTime.now().year.toString(), leagueId).body()
        return creator.fromModelToDataModel(model)
    }
}