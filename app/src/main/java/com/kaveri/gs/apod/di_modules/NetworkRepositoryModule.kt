package com.kaveri.gs.apod.di_modules

import com.kaveri.gs.apod.model.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkRepositoryModule {

    @Provides
    fun getNetworkRepository(): NetworkRepository {
        return NetworkRepository()
    }
}