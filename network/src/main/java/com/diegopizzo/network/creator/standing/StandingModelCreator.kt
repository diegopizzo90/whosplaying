package com.diegopizzo.network.creator.standing

import com.diegopizzo.network.model.StandingDataModel
import com.diegopizzo.network.model.StandingModel

class StandingModelCreator {

    fun fromModelToDataModel(model: StandingModel?): List<StandingDataModel> {
        return model?.response?.first()?.league?.standings?.first()?.map { standing ->
            val team = standing.team
            val all = standing.all
            val goals = all.goals
            StandingDataModel(
                team.id.toString(),
                team.name,
                team.logo,
                standing.rank,
                standing.points,
                standing.goalsDiff,
                standing.form,
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