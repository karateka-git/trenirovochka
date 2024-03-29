package com.example.trenirovochka.domain.interactors.interfaces

import com.example.trenirovochka.domain.models.Program
import com.example.trenirovochka.domain.models.TrainingPlan
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ITrainingProgramsInteractor {
    suspend fun updateTrainingPlan(newTrainingPlan: TrainingPlan)

    fun getTrainingPlan(): Flow<TrainingPlan>

    fun getTrainingProgram(date: Date): Flow<Program>

    fun getTrainingProgram(id: Long): Flow<Program>

    suspend fun updateTrainingProgram(planId: Long, program: Program)

//    suspend fun test()
}
