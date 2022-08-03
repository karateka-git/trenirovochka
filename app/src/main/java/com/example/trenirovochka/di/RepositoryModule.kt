package com.example.trenirovochka.di

import com.example.trenirovochka.data.remote.repositories.TrainingProgramRemoteRepositoryMock
import com.example.trenirovochka.domain.datacontracts.ITrainingProgramRemoteRepository
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
}
