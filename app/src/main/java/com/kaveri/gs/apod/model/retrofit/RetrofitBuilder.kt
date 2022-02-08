package com.kaveri.gs.apod.model.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://api.nasa.gov/planetary/apod/"
    private const val API_KEY = ""

    private val okhttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(Interceptor {
                val originalRequest = it.request()
                val url = originalRequest.url.newBuilder().addQueryParameter(
                    "api_key", API_KEY
                ).build()
                val requestBuilder = originalRequest.newBuilder().url(url)
                val newRequest = requestBuilder.build()
                println("Request url : ${newRequest.url}")
                it.proceed(newRequest)
            }
            ).build()
    }

    val retrofitService: INASAService by lazy {
        getRetrofit().create(INASAService::class.java)
    }

    fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}