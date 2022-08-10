package com.example.trenirovochka.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DeviceModule {

    @Singleton
    @Provides
    fun provideCalendar(): Calendar {
        return Calendar.getInstance()
    }
}