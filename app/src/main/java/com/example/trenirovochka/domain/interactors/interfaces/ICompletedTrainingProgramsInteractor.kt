package com.example.trenirovochka.domain.interactors.interfaces

import com.example.trenirovochka.domain.models.CompletedTrainingProgram
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ICompletedTrainingProgramsInteractor {

    suspend fun add(program: CompletedTrainingProgram)

    suspend fun getAll(): List<CompletedTrainingProgram>

    fun get(date: Date): Flow<CompletedTrainingProgram?>
}
