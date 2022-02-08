package com.kaveri.gs.apod.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.datepicker.MaterialDatePicker
import com.kaveri.gs.apod.R
import com.kaveri.gs.apod.model.pojo.APOD
import com.kaveri.gs.apod.model.pojo.ApodNasa
import com.kaveri.gs.apod.model.repository.APODRepository
import com.kaveri.gs.apod.viewmodel.helper.HelperUtil.convertNASAObjToRoomObj
import com.kaveri.gs.apod.viewmodel.helper.HelperUtil.convertNasaObjToAppObj
import com.kaveri.gs.apod.viewmodel.helper.HelperUtil.convertRoomObjToAppObj
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

    var indexOfItemRemoved: MutableLiveData<Int> = MutableLiveData()
    var selectedDate: MutableLiveData<String> = MutableLiveData()
    var todaysApod: MutableLiveData<APOD> = MutableLiveData()
    private val apodRepository = APODRepository(application.applicationContext)
    val homeDateSelected: MutableLiveData<String> = MutableLiveData()
    var favListOfAPOD: MutableLiveData<ArrayList<APOD>> = MutableLiveData()

    fun init() {
        val recentDate = apodRepository.getRecentDateStored(getApplication())
        if (recentDate.isEmpty()) {
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            selectedDate.value = currentDate
        } else {
            selectedDate.value = recentDate
        }
    }

    /**
     * get [APOD] from to be shown in home page
     * */
    fun getAPOD() {
        if (isRecentDate()) {
            CoroutineScope(Dispatchers.Default).launch {
                var readFromRoomDb: com.kaveri.gs.apod.model.room.APOD? = null
                async {
                    readFromRoomDb = readFromDb(selectedDate.value ?: "")
                }.await()
                withContext(Dispatchers.Main) {
                    readFromRoomDb?.let {
                        todaysApod.value = convertRoomObjToAppObj(it)
                    }
                    if (readFromRoomDb == null) {
                        getAPODFromApi()
                    }
                }
            }
        } else {
            getAPODFromApi()
        }
    }

    private fun getAPODFromApi() {
        selectedDate.value?.let {
            apodRepository.getApodFromApi(getApplication(), it, {
                it?.let {
                    println("APOD received : ${it}")
                    todaysApod.value = convertNasaObjToAppObj(it)
                    storeApodInDb(it)
                }
            }, {
                println("get failed : $it")
                if(it.contains("BAD REQUEST")) {
                    Toast.makeText(getApplication(), R.string.error_msg, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(getApplication(), it, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun isRecentDate(): Boolean {
        return apodRepository.getRecentDateStored(getApplication()).equals(selectedDate.value)
    }

    /**
     * Retrieve the favorite APOD from Room Database
     * */
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


    /**
     *  This method storeds the APOD retrieved from backend in the RoomDb
     *  @param readApodNasa [ApodNasa] object retrieved from the api
     * */
    fun storeApodInDb(readApodNasa: ApodNasa?) {
        val apod: com.kaveri.gs.apod.model.room.APOD = convertNASAObjToRoomObj(readApodNasa)
        runBlocking {
            val apodRead = CoroutineScope(Dispatchers.Default).async {
                apodRepository.insert(apod)
            }.await()
            apodRepository.setRecentDate(getApplication(), apod.date)
        }
    }

    private suspend fun readFromDb(date: String): com.kaveri.gs.apod.model.room.APOD {
        val apodReadFromDb = apodRepository.getAPODData(date)
        return apodReadFromDb
    }


    /**
     * Opens the date picker dialog and uses the date selected for search result
     * */
    fun openDatePicker(fragmentManager: FragmentManager) {
        val datePickerDialog = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .build()
        datePickerDialog.show(fragmentManager, "DATE_PICKER")
        datePickerDialog.addOnPositiveButtonClickListener {
            selectedDate.value =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it))
            apodRepository.setRecentDate(getApplication(), selectedDate.value ?: "")
        }
    }

    /**
     * Add the APOD to favorites
     * */
    fun addToFav(date: String) {
        CoroutineScope(Dispatchers.Default).launch {
            async {
                println("adding to fav")
                apodRepository.addToFav(date)
            }.await()
            val dbAPOD = readFromDb(date)
            withContext(Dispatchers.Main) {
                todaysApod.value = todaysApod.value?.copy(fav = true)
                val favApod = convertRoomObjToAppObj(dbAPOD)
                val list = favListOfAPOD.value ?: arrayListOf()
                list.add(favApod)
                if (favListOfAPOD.value?.contains(favApod) == false) favListOfAPOD.value = list
            }
        }
    }

    /**
     * Removes the APOD from favorites.
     * ToDo: Not in use yet. needs to be handled from the Fav list page
     * */
    fun removeFromFav(date: String) {
        CoroutineScope(Dispatchers.Default).launch {
            println("remove from fav")
            async {
                apodRepository.removeFromFav(date)
            }.await()
            withContext(Dispatchers.Main) {
                var itemTobeRemoved: APOD? = null
                if (date.equals(todaysApod.value?.date) == true) {
                    itemTobeRemoved = todaysApod.value
                    todaysApod.value = todaysApod.value?.copy(fav = false)
                } else {
                    for (item in favListOfAPOD.value!!) {
                        if (item.date.equals(date)) {
                            itemTobeRemoved = item
                            break
                        }
                    }
                }
                println("SDK < N : itemToBeRemoved : ${itemTobeRemoved}")
                itemTobeRemoved?.let {
                    indexOfItemRemoved.value = favListOfAPOD.value?.indexOf(itemTobeRemoved)
                    favListOfAPOD.value?.remove(itemTobeRemoved)
                    println("removed the item")
                }
            }
        }
    }
}