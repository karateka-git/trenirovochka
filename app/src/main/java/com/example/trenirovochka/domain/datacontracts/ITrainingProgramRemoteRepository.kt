package com.example.trenirovochka.domain.datacontracts

import com.example.trenirovochka.domain.models.TrainingProgram
import kotlinx.coroutines.flow.Flow

interface ITrainingProgramRemoteRepository {
    fun getTrainingProgram(date: String): Flow<TrainingProgram>
}
