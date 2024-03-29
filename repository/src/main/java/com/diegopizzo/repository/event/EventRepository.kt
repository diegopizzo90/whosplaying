package com.diegopizzo.repository.event

import com.diegopizzo.network.interactor.event.IEventInteractor
import com.diegopizzo.network.model.EventDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

internal class EventRepository(
    private val interactor: IEventInteractor,
    private val defaultDispatcher: CoroutineDispatcher
) : IEventRepository {

    override fun getEvent(fixtureId: Long): Flow<EventDataModel?> {
        return interactor.getEvents(fixtureId).catch {
            interactor.clearCache()
            emit(null)
        }.flowOn(defaultDispatcher)
    }
}