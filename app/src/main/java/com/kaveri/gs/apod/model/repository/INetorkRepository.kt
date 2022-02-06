package com.kaveri.gs.apod.model.repository

import com.kaveri.gs.apod.model.APOD

/**
 * This interface declares all the apis required by the app
 *
 * */
interface INetworkRepository {
    /**
     *  This method should be implemnted when retrieving the [APOD] data from NASA APOD API
     *  @param date is the date for which the APOD should be retrieved
     *  @param successCallback is to invoke the success callback once the request is succesfull to handle the response data
     *  @param failureCallback is to invoke the failure callback when the request fails
     * */
    fun getApod(date: String, successCallback: (apod: APOD?) -> Unit, failureCallback: (errorMessag: String) -> Unit)

    /**
     *  This method should be implmented when retrieving the [APOD] data from NASA APOD API
     *  @param startDate is the starting date of range for which the APOD should be retrieved
     *  @param endDate is the ending date of range for which the APOD should be retrieved
     *  @param successCallback is to invoke the success callback once the request is succesfull to handle the response data
     *  @param failureCallback is to invoke the failure callback when the request fails
     * */
    fun getApodForDateRange(startDate: String, endDate: String, successCallback: (apod: APOD?) -> Unit, failureCallback: (errorMessag: String) -> Unit)

    fun getRandomApo(successCallback: (apod: APOD?) -> Unit, failureCallback: (errorMessag: String) -> Unit)
}