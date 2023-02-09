package com.example.trenirovochka.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.trenirovochka.data.local.storage.StorageDatabase
import com.example.trenirovochka.data.local.storage.daos.CompletedTrainingProgramsDao
import com.example.trenirovochka.data.local.storage.daos.TrainingPlanDao
import com.example.trenirovochka.data.local.storage.entities.TrainingPlanEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingPlanJoinEntity
import com.example.trenirovochka.domain.models.DaysOfWeek
import com.example.trenirovochka.domain.models.TrainingDay
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Singleton
    @Provides
    fun provideStorageDatabase(
        @ApplicationContext context: Context,
        trainingPlanProvider: Provider<TrainingPlanDao>
    ): StorageDatabase {
        val database: StorageDatabase =
            Room.databaseBuilder(context, StorageDatabase::class.java, "storage_database.db")
                .addCallback(PrepopulateTrainingPlanCallback(trainingPlanProvider)).build()

        // call to create a database
        applicationScope.launch {
            database.runInTransaction {}
        }
        return database
    }

    @Singleton
    @Provides
    fun provideTrainingPlanDao(
        database: StorageDatabase,
    ): TrainingPlanDao =
        database.trainingPlanDao()

    @Singleton
    @Provides
    fun provideCompletedTrainingProgramsDao(
        database: StorageDatabase,
    ): CompletedTrainingProgramsDao =
        database.completedTrainingProgramsDao()

    inner class PrepopulateTrainingPlanCallback(
        private val trainingPlanProvider: Provider<TrainingPlanDao>
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            applicationScope.launch {
                populateDatabase()
            }
        }

        // populate default plans
        private suspend fun populateDatabase() {
            trainingPlanProvider.get().insert(
                TrainingPlanJoinEntity(
                    trainingPlan = TrainingPlanEntity(
                        0,
                        "Название плана",
                        DaysOfWeek.values().map { TrainingDay(it) },
                    ),
                    trainingPrograms = listOf()
                )
            )
        }
    }
}
