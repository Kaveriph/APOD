package com.kaveri.gs.apod.model.retrofit

import com.kaveri.gs.apod.model.APOD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface INASAService {


    @GET(".")
    fun getApod(@Query("date") date: String) : Call<APOD>

    @GET(".")
    fun getApodForDateRange(@Query("start_date") startDate: String, @Query("end_date") endDate: String) : Call<List<APOD>>

    @GET("?count=5")
    fun getRandomApo() : Call<List<APOD>>

}