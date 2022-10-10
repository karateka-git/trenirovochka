package com.example.trenirovochka.domain.datacontracts.storage

import com.example.trenirovochka.data.local.storage.entities.TrainingPlanJoinEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingProgramJoinEntity
import com.example.trenirovochka.domain.models.Program
import kotlinx.coroutines.flow.Flow

interface ITrainingPlanStorageRepository {

    suspend fun savePlan(trainingPlanEntity: TrainingPlanJoinEntity)

    suspend fun getPlans(): List<TrainingPlanJoinEntity>

    fun getSelectedTrainingPlan(): Flow<TrainingPlanJoinEntity>

    suspend fun updateTrainingProgram(trainingProgramJoinEntity: TrainingProgramJoinEntity)
}
