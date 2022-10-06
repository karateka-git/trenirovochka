package com.example.trenirovochka.data.local.storage.repositories

import com.example.trenirovochka.data.local.storage.daos.TrainingPlanDao
import com.example.trenirovochka.data.local.storage.entities.TrainingPlanJoinEntity
import com.example.trenirovochka.domain.datacontracts.storage.ITrainingPlanStorageRepository
import javax.inject.Inject

class TrainingPlanRoomRepository @Inject constructor(
    private val trainingPlanDao: TrainingPlanDao
) : ITrainingPlanStorageRepository {
    override suspend fun savePlan(trainingPlanEntity: TrainingPlanJoinEntity) {
        trainingPlanDao.insert(trainingPlanEntity)
    }

    override suspend fun getPlans(): List<TrainingPlanJoinEntity> {
        return trainingPlanDao.getAll()
    }
}
