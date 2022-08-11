package com.example.trenirovochka.data.remote.repositories

import com.example.trenirovochka.domain.datacontracts.ITrainingProgramRemoteRepository
import com.example.trenirovochka.domain.extensions.formatAsFullDate
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.domain.models.TrainingProgram
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class TrainingProgramRemoteRepositoryMock @Inject constructor(
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val calendar: Calendar,
    // TODO add service
) : ITrainingProgramRemoteRepository {
    override fun getTrainingProgram(date: String) = flow {
        emit(
            TrainingProgram(
                formatAsFullDate(calendar.time),
                "test name",
                listOf(
                    Exercise(
                        "Гиперэкстензия",
                        3,
                        10,
                        "5",
                    ),
                    Exercise(
                        "Гиперэкстензия",
                        3,
                        10,
                        "5",
                    ),
                    Exercise(
                        "Гиперэкстензия",
                        3,
                        10,
                        "5",
                    ),
                    Exercise(
                        "Гиперэкстензия",
                        3,
                        10,
                        "5",
                    ),
                    Exercise(
                        "Гиперэкстензия",
                        3,
                        10,
                        "5",
                    )
                )
            )
        )
    }.flowOn(ioDispatcher)
}
