package com.diegopizzo.network.cache.event

import com.diegopizzo.network.model.EventModel
import retrofit2.Response

interface IEventInteractorCache {
    suspend fun getEventByFixtureId(fixtureId: Long): Response<EventModel>
}
