package com.diegopizzo.whosplaying.repository.event

import com.diegopizzo.network.model.EventDataModel
import kotlinx.coroutines.flow.Flow

interface IEventRepository {
    fun getEvent(fixtureId: Long): Flow<EventDataModel?>
}
