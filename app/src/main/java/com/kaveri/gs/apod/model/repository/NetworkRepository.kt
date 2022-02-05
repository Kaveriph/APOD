package com.kaveri.gs.apod.model.repository

import com.kaveri.gs.apod.model.APOD
import com.kaveri.gs.apod.model.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Response

class NetworkRepository : INetworkRepository {


    override fun getApod(
        date: String,
        successCallback: (apod: APOD?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
        println("calling for date : $date")
        return RetrofitBuilder.retrofitService.getApod(date).enqueue(
            object : retrofit2.Callback<APOD> {
                override fun onResponse(call: Call<APOD>, response: Response<APOD>) {
                    if (response.isSuccessful) {
                        println("response received : ${response.body()}")
                        successCallback(response.body())
                    } else {
                        failureCallback(response.message())
                    }
                }

                override fun onFailure(call: Call<APOD>, t: Throwable) {
                    println("Call failed ${t.stackTrace}")
                }

            }
        )
    }

    override fun getApodForDateRange(
        startDate: String,
        endDate: String,
        successCallback: (apod: APOD?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
    }

    override fun getRandomApo(
        successCallback: (apod: APOD?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
    }
}