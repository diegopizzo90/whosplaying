package com.diegopizzo.whosplaying.config

import com.diegopizzo.network.NetworkConstant.BASE_URL
import com.diegopizzo.network.NetworkConstant.HEADER_KEY_PARAMETER_NAME
import com.diegopizzo.network.service.RetrofitApi
import com.diegopizzo.whosplaying.BuildConfig
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single<RetrofitApi> {

        val httpClient = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader(HEADER_KEY_PARAMETER_NAME, BuildConfig.API_KEY_VALUE)
                    .build()
                it.proceed(request)
            }

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(RetrofitApi::class.java)
    }
}