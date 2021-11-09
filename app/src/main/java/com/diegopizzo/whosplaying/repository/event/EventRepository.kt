package com.diegopizzo.whosplaying.repository.event

import com.diegopizzo.network.interactor.event.IEventInteractor
import com.diegopizzo.network.model.EventDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class EventRepository(
    private val interactor: IEventInteractor,
    private val defaultDispatcher: CoroutineDispatcher
) : IEventRepository {

    override fun getEvent(fixtureId: Long): Flow<EventDataModel?> {
        return interactor.getEvents(fixtureId).flowOn(defaultDispatcher)
    }
}