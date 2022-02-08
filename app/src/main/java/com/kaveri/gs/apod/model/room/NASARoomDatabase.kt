package com.kaveri.gs.apod.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * This is an abstract class to create and instantiate a singleton DATABASE instance
 *
 * */
@Database(entities = arrayOf(APOD::class), version = 1, exportSchema = false)
abstract class NASARoomDatabase : RoomDatabase() {

    abstract fun apodDao(): APODDao

    companion object {
        private const val NASA_DATABASE = "Nasa_Database"
        private var ROOM_DAB_INSTANCE : NASARoomDatabase? = null
        fun getDatabase(context: Context): NASARoomDatabase {
            return ROOM_DAB_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                NASARoomDatabase::class.java,
                NASA_DATABASE).build()
                ROOM_DAB_INSTANCE = instance
                instance
            }
        }
    }
}