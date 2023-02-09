package com.example.trenirovochka.di

import com.example.trenirovochka.domain.interactors.CompletedTrainingProgramsInteractor
import com.example.trenirovochka.domain.interactors.ResourcesInteractor
import com.example.trenirovochka.domain.interactors.TrainingProgramsInteractor
import com.example.trenirovochka.domain.interactors.interfaces.ICompletedTrainingProgramsInteractor
import com.example.trenirovochka.domain.interactors.interfaces.IResourcesInteractor
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
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
    abstract fun bindResourcesInteractor(implementation: ResourcesInteractor): IResourcesInteractor

    @Singleton
    @Binds // TODO rename
    abstract fun bindHomeInteractor(implementation: TrainingProgramsInteractor): ITrainingProgramsInteractor

    @Singleton
    @Binds
    abstract fun bindCompletedTrainingProgramsInteractor(implementation: CompletedTrainingProgramsInteractor): ICompletedTrainingProgramsInteractor
}
