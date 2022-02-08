package com.kaveri.gs.apod.model.repository

import com.kaveri.gs.apod.model.room.APOD

interface IRoomDbRepository {

    /**
     * This is a RoomDb method to insert the retrieved APOD data into database
     * @param [apod]
     * */
    suspend fun insert(apod: APOD)

    /**
     * This is a Room DB method to retrieve  the APOD data for a specified date
     * */
    suspend fun getAPODData(date: String): APOD

    /**
     * This is a Room DB method to retrieve the fav [APOD] data from DB
     * */
    suspend fun getFavAPOD(): List<APOD>?

    /***
     *
     * */
    suspend fun addToFav(date: String)

    /***
     *
     * */
    suspend fun removeFromFav(date: String)
}