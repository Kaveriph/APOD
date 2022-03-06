package com.kaveri.gs.apod.model.repository

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceRepository: ISharedRepository {

    @Inject
    constructor() {
        println("SharedPrefRepo injected")
    }

    private val RECENT_DATE: String? = "RECENT_DATE"
    private val sharedPrefFile = "APODSharedPreference"

    /**
     * getting the shared preference object
     * */
    private fun getSharedPreference(context: Context) : SharedPreferences {
        return context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    }

    override fun getRecentDateStored(context: Context) : String {
        return getSharedPreference(context).getString(RECENT_DATE, "") ?: ""
    }

    override fun setRecentDate(context: Context, date: String) {
        getSharedPreference(context).edit().putString(RECENT_DATE, date).apply()
    }
}