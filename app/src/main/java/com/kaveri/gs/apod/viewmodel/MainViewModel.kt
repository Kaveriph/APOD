package com.kaveri.gs.apod.viewmodel

import android.app.Application
import android.os.Build
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.datepicker.MaterialDatePicker
import com.kaveri.gs.apod.model.pojo.APOD
import com.kaveri.gs.apod.model.pojo.ApodNasa
import com.kaveri.gs.apod.model.repository.APODRepository
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * This Class extends the [AndroidViewModel] to communicate between Model and View
 * @param application required for the base class and context for db access
 * */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    var selectedDate: MutableLiveData<String> = MutableLiveData()
    var todaysApod: MutableLiveData<APOD> = MutableLiveData()
    private val apodRepository = APODRepository(application.applicationContext)
    val homeDateSelected: MutableLiveData<String> = MutableLiveData()
    var favListOfAPOD: MutableLiveData<ArrayList<APOD>> = MutableLiveData()

    fun init() {
        selectedDate.value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun getAPOD() {
        selectedDate.value?.let {
            apodRepository.getApodFromApi(it, {
                it?.let {
                    println("APOD received : ${it.toString()}")
                    todaysApod.value = APOD(
                        date = it.date,
                        explanation = it.explanation,
                        hdurl = it.hdurl,
                        mediaType = it.mediaType,
                        serviceVersion = it.serviceVersion,
                        title = it.title,
                        url = it.url,
                        fav = false
                    )
                    storeApodInDb(it)
                }
            }, {
                println("get failed : $it")
            })
        }
    }


    fun getFavAPOD() {
        CoroutineScope(Dispatchers.Default).launch {
            val favApod = apodRepository.getFavAPOD()
            withContext(Dispatchers.Main) {
                val listOfFavAPOD = ArrayList<APOD>()
                favApod?.let {
                    for (item in it) {
                        val apod: APOD = convertRoomObjToAppObj(item)
                        println("fav found : ${item}")
                        listOfFavAPOD.add(apod)
                    }
                }
                favListOfAPOD.value = listOfFavAPOD
            }
        }
    }

    private fun convertRoomObjToAppObj(item: com.kaveri.gs.apod.model.room.APOD): APOD {
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


    /**
     *  This method storeds the APOD retrieved from backend in the RoomDb
     *  @param readApodNasa [ApodNasa] object retrieved from the api
     * */
    fun storeApodInDb(readApodNasa: ApodNasa?) {
        val apod: com.kaveri.gs.apod.model.room.APOD = convertNASAObjToRoomObj(readApodNasa)
        runBlocking {
            val apodRead = CoroutineScope(Dispatchers.Default).async {
                apodRepository.insert(apod)
            }
            println("${apodRead.await()}")
            readFromDb(apod.date)
        }
    }

    private fun convertNASAObjToRoomObj(readApodNasa: ApodNasa?): com.kaveri.gs.apod.model.room.APOD {
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

    private suspend fun readFromDb(date: String): com.kaveri.gs.apod.model.room.APOD {
        val apodReadFromDb = apodRepository.getAPODData(date)
        println("APOD read from db : ${apodReadFromDb.title} : ${apodReadFromDb.date} :  ${apodReadFromDb.fav}")
        return apodReadFromDb
    }

    fun openDatePicker(fragmentManager: FragmentManager) {
        val datePickerDialog = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .build()
        datePickerDialog.show(fragmentManager, "DATE_PICKER")
        datePickerDialog.addOnPositiveButtonClickListener {
            selectedDate.value =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it))
        }
    }

    fun addToFav(date: String) {
        CoroutineScope(Dispatchers.Default).launch {
            async {
                println("adding to fav")
                apodRepository.addToFav(date)
            }.await()
            val dbAPOD = readFromDb(date)
            withContext(Dispatchers.Main) {
                val favApod = convertRoomObjToAppObj(dbAPOD)
                var list = favListOfAPOD.value ?: arrayListOf()
                list.add(favApod)
                if (favListOfAPOD.value?.contains(favApod) == false) favListOfAPOD.value = list
            }
        }
    }

    fun removeFromFav(date: String) {
        runBlocking {
            withContext(Dispatchers.Default) {
                async {
                    apodRepository.removeFromFav(date)
                }.await()
                withContext(Dispatchers.Main) {
                    //val favApod = convertRoomObjToAppObj(readFromDb(date))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        favListOfAPOD.value?.removeIf { it.date.equals(date) }
                    } else {
                        var itemTobeREmoved: APOD? = null
                        for (item in favListOfAPOD.value!!) {
                            if (item.date.equals(date)) {
                                itemTobeREmoved = item
                                break
                            }
                        }
                        itemTobeREmoved?.let {
                            favListOfAPOD.value?.remove(itemTobeREmoved)
                        }
                    }
                }
            }
        }
    }


}