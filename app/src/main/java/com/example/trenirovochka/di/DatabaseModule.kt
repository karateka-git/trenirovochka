package com.example.trenirovochka.di

import android.content.Context
import androidx.room.Room
import com.example.trenirovochka.data.local.storage.StorageDatabase
import com.example.trenirovochka.data.local.storage.daos.TrainingPlanDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideStorageDatabase(
        @ApplicationContext context: Context,
    ): StorageDatabase =
        Room.databaseBuilder(context, StorageDatabase::class.java, "storage_database.db").build()

    @Singleton
    @Provides
    fun provideTrainingPlanDao(
        database: StorageDatabase,
    ): TrainingPlanDao =
        database.trainingPlanDao()
}
