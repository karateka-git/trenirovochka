package com.example.trenirovochka.domain.interactors.interfaces

import com.example.trenirovochka.domain.models.Program
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ITrainingProgramsInteractor {
    fun getTrainingProgram(date: Date): Flow<Program>
}
