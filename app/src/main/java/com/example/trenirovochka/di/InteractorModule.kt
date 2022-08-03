package com.example.trenirovochka.di

import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import com.example.trenirovochka.domain.interactors.interfaces.TrainingProgramsInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class InteractorModule {

    @Singleton
    @Binds
    abstract fun bindHomeInteractor(implementation: TrainingProgramsInteractor): ITrainingProgramsInteractor
}
