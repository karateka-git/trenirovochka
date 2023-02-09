package com.example.trenirovochka.data.local.storage.repositories

import com.example.trenirovochka.data.local.storage.daos.CompletedTrainingProgramsDao
import com.example.trenirovochka.data.local.storage.entities.CompletedTrainingProgramJoinEntity
import com.example.trenirovochka.domain.datacontracts.storage.ICompletedTrainingProgramsStorageRepository
import com.example.trenirovochka.domain.models.CompletedTrainingProgram
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class CompletedTrainingProgramsRoomRepository @Inject constructor(
    private val completedTrainingProgramsDao: CompletedTrainingProgramsDao,
    @Named("IODispatcher") private val dispatcherIO: CoroutineDispatcher
) : ICompletedTrainingProgramsStorageRepository {

    override suspend fun getAll(): List<CompletedTrainingProgramJoinEntity> =
        withContext(dispatcherIO) {
            completedTrainingProgramsDao.getAll()
        }

    override suspend fun add(completedProgram: CompletedTrainingProgramJoinEntity) =
        withContext(dispatcherIO) {
            completedTrainingProgramsDao.insert(completedProgram)
        }

    override fun get(date: Date): Flow<CompletedTrainingProgramJoinEntity?> =
        completedTrainingProgramsDao.get(date)
}
