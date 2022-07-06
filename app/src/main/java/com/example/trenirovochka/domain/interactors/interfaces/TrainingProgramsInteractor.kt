package com.example.trenirovochka.domain.interactors.interfaces

import com.example.trenirovochka.domain.datacontracts.ITrainingProgramRemoteRepository
import com.example.trenirovochka.domain.models.TrainingProgram
import kotlinx.coroutines.flow.Flow

class TrainingProgramsInteractor(
    private val trainingProgramRemoteRepository: ITrainingProgramRemoteRepository
) : ITrainingProgramsInteractor {

    override fun getTrainingProgram(date: String): Flow<TrainingProgram> =
        trainingProgramRemoteRepository.getTrainingProgram(date)
}
