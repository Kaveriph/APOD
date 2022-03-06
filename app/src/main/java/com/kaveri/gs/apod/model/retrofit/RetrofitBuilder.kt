package com.kaveri.gs.apod.model.retrofit

import javax.inject.Inject

class RetrofitBuilder {

    @Inject
    constructor() {
        println("RetrofitBuilder injected")
    }

    @Inject
    lateinit var retrofitService: INASAService

   /* private lateinit var retrofit: Retrofit

    private var okhttpClient: OkHttpClient? = null*/

   /* private val retrofitService: INASAService by lazy {
        retrofit.create(INASAService::class.java)
    }*/

    /*fun getRetrofitService(context: Context) : INASAService {
        retrofit = getRetrofit(context)
        return retrofitService
    }*/

    /*fun getRetrofit(context: Context): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(Companion.BASE_URL)
            .client(getOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    fun getOkHttpClient(context: Context): OkHttpClient {
        if (okhttpClient == null) {
            okhttpClient = OkHttpClient.Builder()
                .addInterceptor(Interceptor {
                    val originalRequest = it.request()
                    val url = originalRequest.url.newBuilder().addQueryParameter(
                        "api_key", Helper.loadPropertyFile(context, "config.properties").getProperty("NASA_API_KEY")
                    ).build()
                    val requestBuilder = originalRequest.newBuilder().url(url)
                    val newRequest = requestBuilder.build()
                    println("Request url : ${newRequest.url}")
                    it.proceed(newRequest)
                }
                ).build()
        }
        return okhttpClient as OkHttpClient

    }*/

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/planetary/apod/"
        private const val API_KEY = ""
    }
}