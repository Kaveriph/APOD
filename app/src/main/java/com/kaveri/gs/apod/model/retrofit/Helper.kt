package com.kaveri.gs.apod.model.retrofit

import android.content.Context
import java.io.IOException
import java.util.*

object Helper {

    fun loadPropertyFile(context: Context, fileLocation: String): Properties {
        val properties = Properties()
        try {
            val inputStream = context.assets.open(fileLocation)
            properties.load(inputStream)
        } catch (e: IOException) {
            println(e.toString())
        }
        return properties
    }
}