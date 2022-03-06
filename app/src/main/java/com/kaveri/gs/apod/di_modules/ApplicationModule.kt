package com.kaveri.gs.apod.di_modules

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val mApplication: Application) {

    @Provides
    fun provideApplication() : Application {
        println("mApplication injected")
        return mApplication
    }
}