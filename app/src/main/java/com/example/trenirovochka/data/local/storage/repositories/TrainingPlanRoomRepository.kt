package com.example.trenirovochka.data.local.storage.repositories

import com.example.trenirovochka.data.local.storage.daos.TrainingPlanDao
import com.example.trenirovochka.data.local.storage.entities.TrainingPlanJoinEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingProgramJoinEntity
import com.example.trenirovochka.domain.datacontracts.storage.ITrainingPlanStorageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class TrainingPlanRoomRepository @Inject constructor(
    private val trainingPlanDao: TrainingPlanDao,
    @Named("IODispatcher") private val dispatcherIO: CoroutineDispatcher
) : ITrainingPlanStorageRepository {
    override suspend fun savePlan(
        trainingPlanEntity: TrainingPlanJoinEntity
    ) = withContext(dispatcherIO) {
        trainingPlanDao.insert(trainingPlanEntity)
    }

    override suspend fun getPlans(): List<TrainingPlanJoinEntity> = withContext(dispatcherIO) {
        trainingPlanDao.getAll()
    }

    override fun getSelectedTrainingPlan(): Flow<TrainingPlanJoinEntity> {
        return trainingPlanDao.getSelectedTrainingPlan()
    }

    override suspend fun updateTrainingProgram(
        trainingProgramJoinEntity: TrainingProgramJoinEntity
    ) = withContext(dispatcherIO) {
        trainingPlanDao.insert(trainingProgramJoinEntity)
    }
}
