package com.kaveri.gs.apod.model.repository

import com.kaveri.gs.apod.model.APOD

class APODRepository : INetworkRepository {

    private val networkRepository by lazy {
        NetworkRepository()
    }

    override fun getApod(
        date: String,
        successCallback: (apod: APOD?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
        networkRepository.getApod(date, successCallback, failureCallback)
    }

    override fun getApodForDateRange(
        startDate: String,
        endDate: String,
        successCallback: (apod: APOD?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getRandomApo(
        successCallback: (apod: APOD?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

}