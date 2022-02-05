package com.kaveri.gs.apod.model.repository

import com.kaveri.gs.apod.model.APOD

interface INetworkRepository {
    fun getApod(date: String, successCallback: (apod: APOD?) -> Unit, failureCallback: (errorMessag: String) -> Unit)

    fun getApodForDateRange(startDate: String, endDate: String, successCallback: (apod: APOD?) -> Unit, failureCallback: (errorMessag: String) -> Unit)

    fun getRandomApo(successCallback: (apod: APOD?) -> Unit, failureCallback: (errorMessag: String) -> Unit)
}