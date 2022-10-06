package com.example.trenirovochka.domain.datacontracts.storage

import com.example.trenirovochka.data.local.storage.entities.TrainingPlanJoinEntity

interface ITrainingPlanStorageRepository {
    suspend fun savePlan(trainingPlanEntity: TrainingPlanJoinEntity)
    suspend fun getPlans(): List<TrainingPlanJoinEntity>
}
