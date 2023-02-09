package com.example.trenirovochka.domain.interactors

import com.example.trenirovochka.domain.datacontracts.storage.ICompletedTrainingProgramsStorageRepository
import com.example.trenirovochka.domain.extensions.toCompletedTrainingProgramDomain
import com.example.trenirovochka.domain.extensions.toCompletedTrainingProgramJoinEntity
import com.example.trenirovochka.domain.interactors.interfaces.ICompletedTrainingProgramsInteractor
import com.example.trenirovochka.domain.models.CompletedTrainingProgram
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class CompletedTrainingProgramsInteractor @Inject constructor(
    private val completedTrainingProgramsStorageRepository: ICompletedTrainingProgramsStorageRepository,
) : ICompletedTrainingProgramsInteractor {

    override suspend fun add(program: CompletedTrainingProgram) {
        completedTrainingProgramsStorageRepository.add(program.toCompletedTrainingProgramJoinEntity())
    }

    override suspend fun getAll(): List<CompletedTrainingProgram> {
        return completedTrainingProgramsStorageRepository.getAll().map { it.toCompletedTrainingProgramDomain() }
    }

    override fun get(date: Date): Flow<CompletedTrainingProgram?> {
        return completedTrainingProgramsStorageRepository.get(date).map { it?.toCompletedTrainingProgramDomain() }
    }
}
