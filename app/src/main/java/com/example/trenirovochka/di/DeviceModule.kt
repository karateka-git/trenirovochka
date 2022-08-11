package com.example.trenirovochka.di

import com.example.trenirovochka.data.local.repositories.AndroidResourcesRepository
import com.example.trenirovochka.domain.datacontracts.local.IResourcesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DeviceModule {

    companion object {
        @Singleton
        @Provides
        fun provideCalendar(): Calendar {
            return Calendar.getInstance()
        }
    }

    @Singleton
    @Binds
    abstract fun bindResourcesRepository(implementation: AndroidResourcesRepository): IResourcesRepository
}
