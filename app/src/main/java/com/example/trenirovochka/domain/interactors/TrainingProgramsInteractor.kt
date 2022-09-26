package com.example.trenirovochka.domain.interactors

import com.example.trenirovochka.domain.datacontracts.ITrainingProgramRemoteRepository
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import com.example.trenirovochka.domain.models.Program
import com.example.trenirovochka.domain.models.TrainingPlan
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class TrainingProgramsInteractor @Inject constructor(
    private val trainingProgramRemoteRepository: ITrainingProgramRemoteRepository
) : ITrainingProgramsInteractor {

    override fun getTrainingPlan(): Flow<TrainingPlan> =
        trainingProgramRemoteRepository.getTrainingPlan()

    override fun getTrainingProgram(date: Date): Flow<Program> =
        trainingProgramRemoteRepository.getTrainingProgram(date)
}
