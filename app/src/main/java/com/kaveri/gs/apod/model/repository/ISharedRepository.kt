package com.kaveri.gs.apod.model.repository

import android.content.Context

interface ISharedRepository {
    fun getRecentDateStored(context: Context) : String

    fun setRecentDate(context: Context, date: String)
}