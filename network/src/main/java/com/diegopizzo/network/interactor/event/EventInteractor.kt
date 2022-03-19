package com.diegopizzo.network.interactor.event

import com.diegopizzo.network.cache.CacheConstant.EVENT_DURATION_MILLIS
import com.diegopizzo.network.cache.event.IEventInteractorCache
import com.diegopizzo.network.creator.event.EventModelCreator
import com.diegopizzo.network.model.EventDataModel
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
                val eventResponse = cache.getEventByFixtureId(fixtureId)
                val statisticsResponse = cache.getStatistics(fixtureId)
                val lineupsModel = cache.getLineups(fixtureId)

                val teams = eventResponse.body()?.response?.first()?.teams

                val fixtureIds = if (teams != null) {
                    "${teams.home.id}-${teams.away.id}"
                } else null

                val headToHeadModel = fixtureIds?.let { cache.getHeadToHead(it) }

                val result = creator.toEventDataModel(
                    eventResponse.body(),
                    statisticsResponse.body(),
                    lineupsModel.body(),
                    headToHeadModel?.body()
                )
                emit(result)
                delay(refreshIntervalMs)
            }
        }
    }
}