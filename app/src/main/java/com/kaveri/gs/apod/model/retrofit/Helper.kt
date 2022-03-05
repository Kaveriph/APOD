package com.kaveri.gs.apod.model.retrofit

import android.app.Application
import android.content.Context
import java.io.IOException
import java.util.*

object Helper {

    const val fileLocation: String = "config.properties"
    private lateinit var context: Application

    val apiKeyVal : String by lazy {
        loadPropertyFile(context, fileLocation).getProperty("NASA_API_KEY")
    }

    private fun loadPropertyFile(context: Context, fileLocation: String): Properties {
        val properties = Properties()
        try {
            val inputStream = context.assets.open(fileLocation)
            properties.load(inputStream)
        } catch (e: IOException) {
            println(e.toString())
        }
        return properties
    }

    fun getApiKey(context: Context, fileLocation: String) : String {
        this.context = context
        //this.fileLocation = fileLocation
        //apiKeyVal = loadPropertyFile(context, "config.properties").getProperty("NASA_API_KEY")
        return apiKeyVal
    }
}