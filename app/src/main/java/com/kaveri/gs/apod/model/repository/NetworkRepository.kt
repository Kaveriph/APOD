package com.kaveri.gs.apod.model.repository

import com.kaveri.gs.apod.model.pojo.ApodNasa
import com.kaveri.gs.apod.model.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Response


/**
 * This class implements the [INetworkRepository] interface
 * and provides implementation for accessing the apis
 * */
class NetworkRepository : INetworkRepository {


    /**
     *  This method retrieved the [ApodNasa] data from NASA APOD API
     *  @param date is the date for which the APOD should be retrieved
     *  @param successCallback is to invoke the success callback once the request is succesfull to handle the response data
     *  @param failureCallback is to invoke the failure callback when the request fails
     * */
    override fun getApodFromApi(
        date: String,
        successCallback: (apodNasa: ApodNasa?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
        println("calling for date : $date")
        return RetrofitBuilder.retrofitService.getApod(date).enqueue(
            object : retrofit2.Callback<ApodNasa> {
                override fun onResponse(call: Call<ApodNasa>, response: Response<ApodNasa>) {
                    if (response.isSuccessful) {
                        println("response received : ${response.body()}")
                        successCallback(response.body())
                    } else {
                        println("Failure: response received : ${response.message()}")
                        failureCallback(response.message())
                    }
                }

                override fun onFailure(call: Call<ApodNasa>, t: Throwable) {
                    println("Call failed ${t.stackTrace}")
                }

            }
        )
    }

    /**
     *  This method retrieved the [ApodNasa] data from NASA APOD API
     *  @param startDate is the starting date of range for which the APOD should be retrieved
     *  @param endDate is the ending date of range for which the APOD should be retrieved
     *  @param successCallback is to invoke the success callback once the request is succesfull to handle the response data
     *  @param failureCallback is to invoke the failure callback when the request fails
     * */
    override fun getApodForDateRange(
        startDate: String,
        endDate: String,
        successCallback: (apodNasa: ApodNasa?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
    }

    override fun getRandomApo(
        successCallback: (apodNasa: ApodNasa?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
    }
}