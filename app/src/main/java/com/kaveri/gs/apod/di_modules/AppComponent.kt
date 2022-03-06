package com.kaveri.gs.apod.di_modules

import com.kaveri.gs.apod.model.repository.APODRepository
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, ContextModule::class])
interface AppComponent {
    //fun getApplication(): Application
    fun getAPODRepository(): APODRepository
    //fun getNetworkRepository(): NetworkRepository
}