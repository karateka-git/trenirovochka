package com.example.trenirovochka.domain.datacontracts

import com.example.trenirovochka.domain.models.Program
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ITrainingProgramRemoteRepository {
    fun getTrainingProgram(date: Date): Flow<Program>
}
