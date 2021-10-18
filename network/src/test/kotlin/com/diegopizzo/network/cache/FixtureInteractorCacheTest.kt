package com.diegopizzo.network.cache

import com.diegopizzo.network.cache.fixture.FixtureInteractorCache
import com.diegopizzo.network.cache.fixture.IFixtureInteractorCache
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.network.service.RetrofitApi
import com.diegopizzo.network.testutil.enqueueResponse
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FixtureInteractorCacheTest {

    private lateinit var server: MockWebServer

    private lateinit var api: RetrofitApi

    private lateinit var cache: IFixtureInteractorCache

    @Before
    fun setUp() {
        server = MockWebServer()

        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(RetrofitApi::class.java)
        cache = FixtureInteractorCache(api)
    }

    @After
    fun shutDown() {
        server.shutdown()
    }

    @Test
    fun getFixture_recordedRequestAndCached_assertEqualsTrue() {
        server.enqueueResponse("fixtures_success_response.json", 200)
        runBlocking {
            //First request, cache empty response retrieved from the API and saved in the cache
            cache.getFixturesByLeagueIdAndByDate(
                anyString(),
                anyString(),
                anyString(),
                anyString()
            )
            val request1 = server.takeRequest(100L, TimeUnit.MILLISECONDS)
            assertEquals(request1?.path?.equals("/fixtures?league=&season=&from=&to="), true)

            //Second request, cache not empty response retrieved from the cache. API not called
            cache.getFixturesByLeagueIdAndByDate(
                anyString(),
                anyString(),
                anyString(),
                anyString()
            )

            val request2 = server.takeRequest(100L, TimeUnit.MILLISECONDS)
            assertEquals(request2?.path, null)
        }
    }
}