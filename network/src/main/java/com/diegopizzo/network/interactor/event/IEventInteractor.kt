package com.diegopizzo.network.interactor.event

import com.diegopizzo.network.model.EventDataModel
import kotlinx.coroutines.flow.Flow

interface IEventInteractor {
    fun getEvents(fixtureId: Long): Flow<EventDataModel?>
    suspend fun clearCache()
}
