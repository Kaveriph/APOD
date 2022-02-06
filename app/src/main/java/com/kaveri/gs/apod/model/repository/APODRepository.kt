package com.kaveri.gs.apod.model.repository

import android.content.Context
import com.kaveri.gs.apod.model.APOD


/**
 * A common Repository that implements all the interfaces for accessing data from Model
 * RoomDB / Network
 * @param context Context of the application
 * */
class APODRepository(context: Context) : INetworkRepository, IRoomDbRepository {

    private val networkRepository by lazy {
        NetworkRepository()
    }

    private val roomDbRepository by lazy {
        RoomDBRepository(context)
    }

    /**
     *  This method retrieved the [APOD] data from NASA APOD API
     *  @param date is the date for which the APOD should be retrieved
     *  @param successCallback is to invoke the success callback once the request is succesfull to handle the response data
     *  @param failureCallback is to invoke the failure callback when the request fails
     * */
    override fun getApod(
        date: String,
        successCallback: (apod: APOD?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
        networkRepository.getApod(date, successCallback, failureCallback)
    }

    /**
     *  This method retrieved the [APOD] data from NASA APOD API
     *  @param startDate is the starting date of range for which the APOD should be retrieved
     *  @param endDate is the ending date of range for which the APOD should be retrieved
     *  @param successCallback is to invoke the success callback once the request is succesfull to handle the response data
     *  @param failureCallback is to invoke the failure callback when the request fails
     * */
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

    /**
     * This is a RoomDb method to insert the retrieved APOD data into database
     * @param [apod]
     * */
    override suspend fun insert(apod: com.kaveri.gs.apod.model.room.APOD) {
        roomDbRepository.insert(apod)
    }

    /**
     * This is a Room DB method to retrieve  the APOD data for a specified date
     * */
    override suspend fun getAPODData(date: String): com.kaveri.gs.apod.model.room.APOD {
        return roomDbRepository.getAPODData(date)
    }

    /**
     * This is a Room DB method to retrieve the fav [APOD] data from DB
     * */
    override suspend fun getFavAPOD(): List<com.kaveri.gs.apod.model.room.APOD>? {
        return roomDbRepository.getFavAPOD()
    }

}