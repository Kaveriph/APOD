package com.kaveri.gs.apod.di_modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(val context: Context) {

    @ApplicationScope
    @Provides
    fun getAppContext(): Context {
        println("Context injected")
        return context
    }
}