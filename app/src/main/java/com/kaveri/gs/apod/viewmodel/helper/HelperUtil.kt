package com.kaveri.gs.apod.viewmodel.helper

import com.kaveri.gs.apod.model.pojo.APOD
import com.kaveri.gs.apod.model.pojo.ApodNasa

object HelperUtil {

    fun convertNASAObjToRoomObj(readApodNasa: ApodNasa?): com.kaveri.gs.apod.model.room.APOD {
        val apod: com.kaveri.gs.apod.model.room.APOD = com.kaveri.gs.apod.model.room.APOD(
            date = readApodNasa?.date ?: "",
            explanation = readApodNasa?.explanation ?: "",
            hdurl = readApodNasa?.hdurl ?: "",
            media_type = readApodNasa?.mediaType ?: "",
            service_version = readApodNasa?.serviceVersion ?: "",
            title = readApodNasa?.title ?: "",
            url = readApodNasa?.url ?: "",
            fav = false
        )
        return apod
    }

    fun convertRoomObjToAppObj(item: com.kaveri.gs.apod.model.room.APOD): APOD {
        val apod: APOD = APOD(
            date = item.date,
            explanation = item.explanation,
            hdurl = item.hdurl,
            mediaType = item.media_type,
            serviceVersion = item.service_version,
            title = item.title,
            url = item.url,
            fav = item.fav
        )
        return apod
    }

}