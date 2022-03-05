package com.diegopizzo.network.interactor.event

import com.diegopizzo.network.cache.CacheConstant.EVENT_DURATION_MILLIS
import com.diegopizzo.network.cache.event.IEventInteractorCache
import com.diegopizzo.network.creator.event.EventModelCreator
import com.diegopizzo.network.model.EventDataModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class EventInteractor(
    private val cache: IEventInteractorCache,
    private val creator: EventModelCreator,
    private val refreshIntervalMs: Long = EVENT_DURATION_MILLIS
) : IEventInteractor {

    override fun getEvents(fixtureId: Long): Flow<EventDataModel?> {
        return flow {
            while (true) {
                val response = cache.getEventByFixtureId(fixtureId)
                val statisticsResponse = cache.getStatistics(fixtureId)
                val lineupsModel = cache.getLineups(fixtureId)

                val result = creator.toEventDataModel(
                    response.body(),
                    statisticsResponse.body(),
                    lineupsModel.body()
                )

                emit(result)
                delay(refreshIntervalMs)
            }
        }
    }
}