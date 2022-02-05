package com.kaveri.gs.apod.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaveri.gs.apod.model.APOD
import com.kaveri.gs.apod.model.repository.APODRepository
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    var todaysApod: MutableLiveData<APOD> = MutableLiveData()
    private val apodRepository = APODRepository()
    val homeDateSelected: MutableLiveData<String> = MutableLiveData()

    fun getAPOD() {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        apodRepository.getApod(currentDate, {
            println("APOD received : ${it.toString()}")
            todaysApod.value = it
        }, {
            println("get failed : $it")
        })
    }
}