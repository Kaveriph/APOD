package com.kaveri.gs.apod.model.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is the table to hold Picture of the Day
 * */
@Entity(tableName = "APOD")
class APOD(@PrimaryKey @ColumnInfo(name= "date") @NonNull val date: String,
val explanation:String, val hdurl: String, val media_type: String, val service_version : String, val title: String, val url:String, val fav:Boolean) {
}