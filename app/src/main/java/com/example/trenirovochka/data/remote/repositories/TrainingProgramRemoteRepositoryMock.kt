package com.example.trenirovochka.data.remote.repositories

import com.example.trenirovochka.domain.datacontracts.ITrainingProgramRemoteRepository
import com.example.trenirovochka.domain.models.TrainingProgram
import kotlinx.coroutines.flow.flow

class TrainingProgramRemoteRepositoryMock : ITrainingProgramRemoteRepository {
    override fun getTrainingProgram(date: String) = flow {
        emit(
            TrainingProgram(
                "01.02.2020",
                "test name"
            )
        )
    }
}
