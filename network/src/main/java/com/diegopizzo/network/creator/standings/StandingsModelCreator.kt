package com.diegopizzo.network.creator.standings

import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.network.model.StandingsModel

class StandingsModelCreator {

    fun fromModelToDataModel(model: StandingsModel?): List<StandingsDataModel> {
        return model?.response?.first()?.league?.standings?.first()?.map { standings ->
            val team = standings.team
            val all = standings.all
            val goals = all.goals
            StandingsDataModel(
                team.id.toString(),
                team.name,
                team.logo,
                standings.rank,
                standings.points,
                standings.goalsDiff,
                standings.form,
                all.played,
                all.win,
                all.draw,
                all.lose,
                goals.scored,
                goals.against
            )
        } ?: emptyList()
    }
}