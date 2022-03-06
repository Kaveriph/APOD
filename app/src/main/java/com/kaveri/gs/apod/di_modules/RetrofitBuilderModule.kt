package com.kaveri.gs.apod.di_modules

import android.content.Context
import com.kaveri.gs.apod.model.retrofit.Helper
import com.kaveri.gs.apod.model.retrofit.INASAService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [ContextModule::class])
class RetrofitBuilderModule {
    private val BASE_URL = "https://api.nasa.gov/planetary/apod/"
    private val API_KEY = ""


    @Provides
    fun getOkhttpClient(context: Context): OkHttpClient {
        val okhttpClient = OkHttpClient.Builder()
            .addInterceptor(getInterceptor(context)).build()
        println("okhttpClient provided/injected")
        return okhttpClient
    }

    @Provides
    fun getInterceptor(context: Context) = Interceptor {
        val originalRequest = it.request()
        val url = originalRequest.url.newBuilder().addQueryParameter(
            "api_key",
            Helper.loadPropertyFile(context, "config.properties").getProperty("NASA_API_KEY")
        ).build()
        val requestBuilder = originalRequest.newBuilder().url(url)
        val newRequest = requestBuilder.build()
        println("Request url : ${newRequest.url}")
        println("Interceptor provided/injected")
        it.proceed(newRequest)
    }

    @Provides
    fun getRetrofit(context: Context): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkhttpClient(context = context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        println("retrofit provided/injected")
        return retrofit
    }

    @Provides
    fun getRetrofitService(context: Context): INASAService {
        println("retrofitService provided/injected")
        return getRetrofit(context).create(INASAService::class.java)
    }
}