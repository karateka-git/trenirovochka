package com.example.trenirovochka.domain.datacontracts.storage

import com.example.trenirovochka.data.local.storage.entities.CompletedTrainingProgramJoinEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ICompletedTrainingProgramsStorageRepository {

    suspend fun getAll(): List<CompletedTrainingProgramJoinEntity>

    suspend fun add(completedProgram: CompletedTrainingProgramJoinEntity)

    fun get(date: Date): Flow<CompletedTrainingProgramJoinEntity?>
}
