package com.kaveri.gs.apod.di_modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val context: Context) {

    @Provides
    fun getAppContext(): Context {
        println("Context injected")
        return context
    }
}