package com.kaveri.gs.apod.model.room

import androidx.room.*

/***
 * This is Data Access Object interface defining the Database queries required for the app
 * on [APOD] table
 * */
@Dao
interface APODDao {

    /**
     * This method inserts the [APOD] data into the DB
     * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(apod:APOD)

    /**
     * This method updates the [APOD] entry in the database to set it as favorite
     * */
    @Query("UPDATE APOD SET fav = :fav WHERE date = :date")
    suspend fun setFav(date: String, fav: Boolean = true)

    /**
     * This method returns only the favorite [APOD]
     * */
    @Query("SELECT * FROM APOD WHERE fav = :fav")
    suspend fun getFav(fav:Boolean = true): List<APOD>?

    /**
     * This method returns the [APOD] from the database for a particular date
     * */
    @Query("SELECT * FROM APOD WHERE date = :date")
    suspend fun getAPOD(date:String): APOD
}