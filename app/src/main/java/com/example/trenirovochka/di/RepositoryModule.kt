package com.example.trenirovochka.di

import com.example.trenirovochka.data.local.storage.repositories.CompletedTrainingProgramsRoomRepository
import com.example.trenirovochka.data.local.storage.repositories.TrainingPlanRoomRepository
import com.example.trenirovochka.data.remote.repositories.TrainingProgramRemoteRepositoryMock
import com.example.trenirovochka.domain.datacontracts.ITrainingProgramRemoteRepository
import com.example.trenirovochka.domain.datacontracts.storage.ICompletedTrainingProgramsStorageRepository
import com.example.trenirovochka.domain.datacontracts.storage.ITrainingPlanStorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTaskRepository(implementation: TrainingProgramRemoteRepositoryMock): ITrainingProgramRemoteRepository

    @Singleton
    @Binds
    abstract fun bindTrainingPlanStorageRepository(implementation: TrainingPlanRoomRepository): ITrainingPlanStorageRepository

    @Singleton
    @Binds
    abstract fun bindCompletedTrainingProgramsRepository(implementation: CompletedTrainingProgramsRoomRepository): ICompletedTrainingProgramsStorageRepository
}
