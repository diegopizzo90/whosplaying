package com.diegopizzo.network.testutil

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

internal fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    val body = this::class.java.getResource("/mockjson/$fileName").readText()
    enqueue(MockResponse().setResponseCode(code).setBody(body))
}