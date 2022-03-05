package com.kaveri.gs.apod.app

import android.app.Application
import com.kaveri.gs.apod.model.repository.NetworkRepository
import com.kaveri.gs.apod.model.repository.RoomDBRepository
import com.kaveri.gs.apod.model.repository.SharedPreferenceRepository
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class APODApplication : Application() {
    val apodAppModule: Module = module {
        single { NetworkRepository() }
        single { SharedPreferenceRepository() }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(apodAppModule)
        }
    }
}