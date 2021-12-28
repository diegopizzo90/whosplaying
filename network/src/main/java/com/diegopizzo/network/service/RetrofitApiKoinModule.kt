package com.diegopizzo.network.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun retrofitModule(baseUrl: String, apiKeyValue: String) = module {
    single<RetrofitApi> {

        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader(HEADER_KEY_PARAMETER_NAME, apiKeyValue)
                    .build()
                it.proceed(request)
            }.addNetworkInterceptor(interceptor)

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(RetrofitApi::class.java)
    }
}

private const val HEADER_KEY_PARAMETER_NAME = "x-rapidapi-key"