package com.example.trenirovochka.data.remote.repositories

import com.example.trenirovochka.domain.datacontracts.ITrainingProgramRemoteRepository
import com.example.trenirovochka.domain.extensions.beforeOrEqualWithoutTime
import com.example.trenirovochka.domain.extensions.formatAsFullDate
import com.example.trenirovochka.domain.models.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class TrainingProgramRemoteRepositoryMock @Inject constructor(
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    // TODO add service
) : ITrainingProgramRemoteRepository {

    private val trainingProgramList = listOf(
        TrainingProgram(
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
    )
    private val trainingTypeMock = Training(
        "Тренировка 1",
        DaysOfWeek.values().map {
            when (it) {
                DaysOfWeek.MONDAY,
                DaysOfWeek.WEDNESDAY,
                DaysOfWeek.FRIDAY -> TrainingDay(it, true)
                else -> TrainingDay(it)
            }
        },
        trainingProgramList,
    )
    private val performedTrainingProgramList = listOf(
        PerformedTrainingProgram(
            formatAsFullDate(
                Calendar.getInstance().apply { roll(Calendar.DAY_OF_YEAR, false) }.time
            ),
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

    override fun getTrainingPlan(): Flow<Training> = flow {
        emit(trainingTypeMock)
    }.flowOn(ioDispatcher)

    override fun getTrainingProgram(date: Date) = flow {
        val trainingProgram =
            performedTrainingProgramList.find { it.date == formatAsFullDate(date) }
                ?: getTrainingProgramFromTrainingType(date)

        emit(trainingProgram)
    }.flowOn(ioDispatcher)

    private fun getTrainingProgramFromTrainingType(date: Date): Program {
        val currentDay = Calendar.getInstance().time
        val dayOfWeek = DaysOfWeek.getDayOfWeek(
            Calendar.getInstance().apply { time = date }
        )
        val selectedTrainingDays = trainingTypeMock.trainingDays.filter { it.isSelected }.map { it.dayOfWeek }

        return if (currentDay.beforeOrEqualWithoutTime(date) &&
            selectedTrainingDays.contains(dayOfWeek)
        ) {
            trainingTypeMock.trainingPrograms[
                selectedTrainingDays.indexOf(dayOfWeek) % trainingTypeMock.trainingPrograms.size
            ]
        } else {
            EmptyProgram()
        }
    }
}
