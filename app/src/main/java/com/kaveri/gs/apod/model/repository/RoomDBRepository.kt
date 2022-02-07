package com.kaveri.gs.apod.model.repository

import android.content.Context
import com.kaveri.gs.apod.model.room.APOD
import com.kaveri.gs.apod.model.room.NASARoomDatabase

/**
 * The Roon Batabase repository implements the [IRoomDbRepository] interface
 * This class implements the definitions for all the Database access queries for the d=table [APOD]
 * */
class RoomDBRepository(context: Context) : IRoomDbRepository {

    private val apodDao by lazy {
        NASARoomDatabase.getDatabase(context).apodDao()
    }

    override suspend fun insert(apod: APOD) {
        apodDao.insert(apod)
    }

    override suspend fun getAPODData(date: String): APOD {
        return apodDao.getAPOD(date)
    }

    override suspend fun getFavAPOD(): List<APOD>? {
        println("RoomDBRepository: calling getFavAPOD")
        return apodDao.getFav()
    }

    override suspend fun addToFav(date: String) {
        return apodDao.setFav(date, true)
    }

    override suspend fun removeFromFav(date: String) {
        return apodDao.setFav(date, false)
    }

}