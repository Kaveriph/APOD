package com.kaveri.gs.apod.viewmodel

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.datepicker.MaterialDatePicker
import com.kaveri.gs.apod.model.APOD
import com.kaveri.gs.apod.model.repository.APODRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

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

    fun init() {
        selectedDate?.value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun getAPOD() {
        selectedDate.value?.let {
            apodRepository.getApod(it, {
                println("APOD received : ${it.toString()}")
                todaysApod.value = it
                storeApodInDb(it)
            }, {
                println("get failed : $it")
            })
        }
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
}