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
                coroutineScope {
                    val eventResponse = async { cache.getEventByFixtureId(fixtureId) }
                    val statisticsResponse = async { cache.getStatistics(fixtureId) }
                    val lineupsModel = async { cache.getLineups(fixtureId) }

                    val teams = eventResponse.await().body()?.response?.first()?.teams
                    val headToHeadModel = if (teams != null) {
                        val fixtureIds = "${teams.home.id}-${teams.away.id}"
                        async { cache.getHeadToHead(fixtureIds) }
                    } else null

                    val result = creator.toEventDataModel(
                        eventResponse.await().body(),
                        statisticsResponse.await().body(),
                        lineupsModel.await().body(),
                        headToHeadModel?.await()?.body()
                    )
                    emit(result)
                    delay(refreshIntervalMs)
                }
            }
        }
    }
}