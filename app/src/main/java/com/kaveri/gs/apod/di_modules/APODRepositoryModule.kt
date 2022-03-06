package com.kaveri.gs.apod.di_modules

import android.content.Context
import com.kaveri.gs.apod.model.repository.APODRepository
import dagger.Module
import dagger.Provides

@Module(includes = [ContextModule::class])
class APODRepositoryModule() {

    @Provides
    fun getAPODRepository() : APODRepository {
        return  APODRepository()
    }
}