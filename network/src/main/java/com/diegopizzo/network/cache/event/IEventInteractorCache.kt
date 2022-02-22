package com.diegopizzo.network.cache.event

import com.diegopizzo.network.model.EventModel
import com.diegopizzo.network.model.StatisticsModel
import retrofit2.Response

interface IEventInteractorCache {
    suspend fun getEventByFixtureId(fixtureId: Long): Response<EventModel>
    suspend fun getStatistics(fixtureId: Long): Response<StatisticsModel>
}
