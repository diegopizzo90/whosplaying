package com.diegopizzo.network.interactor.event

import com.diegopizzo.network.cache.CacheConstant.EVENT_DURATION_SECONDS
import com.diegopizzo.network.cache.event.IEventInteractorCache
import com.diegopizzo.network.creator.event.EventModelCreator
import com.diegopizzo.network.model.EventDataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class EventInteractor(
    private val cache: IEventInteractorCache,
    private val creator: EventModelCreator,
    private val refreshIntervalMs: Int = EVENT_DURATION_SECONDS
) : IEventInteractor {

    override fun getEvents(fixtureId: Long): Flow<EventDataModel?> {
        return flow {
            while (true) {
                val response = cache.getEventByFixtureId(fixtureId)
                val dataModel = creator.toEventDataModel(response.body())
                emit(dataModel)
                delay(refreshIntervalMs.toLong())
            }
        }
    }
}