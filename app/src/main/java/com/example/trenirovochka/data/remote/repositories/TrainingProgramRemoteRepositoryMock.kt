package com.example.trenirovochka.data.remote.repositories

import com.example.trenirovochka.domain.datacontracts.ITrainingProgramRemoteRepository
import com.example.trenirovochka.domain.extensions.formatAsFullDate
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.domain.models.TrainingProgram
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.DayOfWeek
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class TrainingProgramRemoteRepositoryMock @Inject constructor(
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val calendar: Calendar,
    // TODO add service
) : ITrainingProgramRemoteRepository {
    private val trainingProgramList = listOf(
        TrainingProgram(
            formatAsFullDate(Calendar.getInstance().time),
            "test name",
            listOf(
                Exercise(
                    "Ги",
                    3,
                    0,
                    10,
                    "5",
                ),
                Exercise(
                    "Гипер",
                    3,
                    0,
                    10,
                    "5",
                ),
                Exercise(
                    "Гиперэкс",
                    3,
                    0,
                    10,
                    "5",
                ),
                Exercise(
                    "Гиперэкстен",
                    3,
                    0,
                    10,
                    "5",
                ),
                Exercise(
                    "Гиперэкстензия",
                    3,
                    0,
                    10,
                    "5",
                )
            )
        ),
        TrainingProgram(
            formatAsFullDate(Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }.time),
            "test name",
            listOf(
                Exercise(
                    "По",
                    3,
                    0,
                    10,
                    "5",
                ),
                Exercise(
                    "Подтя",
                    3,
                    0,
                    10,
                    "5",
                ),
                Exercise(
                    "Подтяги",
                    3,
                    0,
                    10,
                    "5",
                ),
                Exercise(
                    "Подтягива",
                    3,
                    0,
                    10,
                    "5",
                ),
                Exercise(
                    "Подтягивания",
                    3,
                    0,
                    10,
                    "5",
                )
            )
        )
    )

    override fun getTrainingProgram(date: String) = flow {
        trainingProgramList.find { it.date == date }?.let {
            emit(
                it
            )
        }
    }.flowOn(ioDispatcher)
}
