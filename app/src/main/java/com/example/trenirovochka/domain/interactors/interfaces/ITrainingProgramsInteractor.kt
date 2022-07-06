package com.example.trenirovochka.domain.interactors.interfaces

import com.example.trenirovochka.domain.models.TrainingProgram
import kotlinx.coroutines.flow.Flow

interface ITrainingProgramsInteractor {
    fun getTrainingProgram(date: String): Flow<TrainingProgram>
}
