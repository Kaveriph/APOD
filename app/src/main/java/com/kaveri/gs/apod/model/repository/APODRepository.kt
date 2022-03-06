package com.kaveri.gs.apod.model.repository

import android.content.Context
import com.kaveri.gs.apod.di_modules.DaggerAppComponent
import com.kaveri.gs.apod.di_modules.NetworkRepositoryModule
import com.kaveri.gs.apod.model.pojo.ApodNasa
import javax.inject.Inject


/**
 * A common Repository that implements all the interfaces for accessing data from Model
 * RoomDB / Network
 * @param context Context of the application
 * */
class APODRepository: INetworkRepository, IRoomDbRepository, ISharedRepository {

    @Inject
    constructor() {
        println("APODRepository injected")
    }
    /*private val networkRepository by lazy {
        NetworkRepository()
    }*/
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var networkRepository: NetworkRepository

    init {
    }

    private val roomDbRepository by lazy {
        RoomDBRepository(context)
    }

    private val  sharedPrefRepository by lazy {
        SharedPreferenceRepository()
    }

    /**
     *  This method retrieved the [ApodNasa] data from NASA APOD API
     *  @param date is the date for which the APOD should be retrieved
     *  @param successCallback is to invoke the success callback once the request is succesfull to handle the response data
     *  @param failureCallback is to invoke the failure callback when the request fails
     * */
    override fun getApodFromApi(
        context: Context,
        date: String,
        successCallback: (apodNasa: ApodNasa?) -> Unit,
        failureCallback: (errorMessag: String) -> Unit
    ) {
        networkRepository.getApodFromApi(context,date, successCallback, failureCallback)
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
     * This is a Room DB method to retrieve the fav [ApodNasa] data from DB
     * */
    override suspend fun getFavAPOD(): List<com.kaveri.gs.apod.model.room.APOD>? {
        println("APODRepository: calling getFavAPOD")
        return roomDbRepository.getFavAPOD()
    }

 /*   suspend fun getFavorites(): List<APOD> {
        val favApod = getFavAPOD()
        return listOfFavAPOD
    }*/

    override suspend fun addToFav(date: String) {
        return roomDbRepository.addToFav(date)
    }

    override suspend fun removeFromFav(date: String) {
        return roomDbRepository.removeFromFav(date)
    }

    override fun getRecentDateStored(context: Context): String {
        return sharedPrefRepository.getRecentDateStored(context)
    }

    override fun setRecentDate(context: Context, date: String) {
        sharedPrefRepository.setRecentDate(context, date)
    }
}