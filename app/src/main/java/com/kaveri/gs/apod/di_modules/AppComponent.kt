package com.kaveri.gs.apod.di_modules

import com.kaveri.gs.apod.model.repository.APODRepository
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, RetrofitBuilderModule::class])
interface AppComponent {
    //fun getApplication(): Application
    fun getAPODRepository(): APODRepository
    //fun getRetrofitService(): INASAService
    //fun getNetworkRepository(): NetworkRepository
}