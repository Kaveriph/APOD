package com.kaveri.gs.apod.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kaveri.gs.apod.model.APOD
import com.kaveri.gs.apod.model.repository.APODRepository
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * This Class extends the [AndroidViewModel] to communicate between Model and View
 * @param application required for the base class and context for db access
* */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    var todaysApod: MutableLiveData<APOD> = MutableLiveData()
    private val apodRepository = APODRepository(application.applicationContext)
    val homeDateSelected: MutableLiveData<String> = MutableLiveData()

    fun getAPOD() {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        apodRepository.getApod(currentDate, {
            println("APOD received : ${it.toString()}")
            todaysApod.value = it
            storeApodInDb(it)
        }, {
            println("get failed : $it")
        })
    }


    /**
     *  This method storeds the APOD retrieved from backend in the RoomDb
     *  @param readApod [APOD] object retrieved from the api
     * */
    fun storeApodInDb(readApod: APOD?) {
        val apod: com.kaveri.gs.apod.model.room.APOD = com.kaveri.gs.apod.model.room.APOD(
            date = readApod?.date ?: "",
            explanation = readApod?.explanation ?: "",
            hdurl = readApod?.hdurl ?: "",
            media_type = readApod?.mediaType ?: "",
            service_version = readApod?.serviceVersion ?: "",
            title = readApod?.title ?: "",
            url = readApod?.url ?: "",
            fav = false
        )
        runBlocking {
            val apodRead = GlobalScope.async {
                apodRepository.insert(apod)
            }
            println("${apodRead.await()}")
            val apodReadFromDb = apodRepository.getAPODData(apod.date)
            println("APOD read from db : ${apodReadFromDb.title} : ${apodReadFromDb.date}")
        }
    }
}