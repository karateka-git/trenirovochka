package com.example.trenirovochka.data.remote.repositories

import com.example.trenirovochka.domain.datacontracts.ITrainingProgramRemoteRepository
import com.example.trenirovochka.domain.extensions.compareWithoutTime
import com.example.trenirovochka.domain.extensions.formatAsFullDate
import com.example.trenirovochka.domain.models.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.time.DurationUnit
import kotlin.time.toDuration

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
    )
    private val trainingTypeMock = Training(
        "Тренировка 1",
        Calendar.getInstance().time,
        listOf(
            DayOfWeekCalendarAdapter.MONDAY,
            DayOfWeekCalendarAdapter.WEDNESDAY,
            DayOfWeekCalendarAdapter.FRIDAY
        ),
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
        val performedTraining =
            performedTrainingProgramList.find { it.date == formatAsFullDate(date) }
        val trainingProgram = getTrainingProgramFromTrainingType(date)

        emit((performedTraining ?: trainingProgram))
    }.flowOn(ioDispatcher)

    private fun getTrainingProgramFromTrainingType(date: Date): Program {
        val dayOfWeek = DayOfWeekCalendarAdapter.getDayOfWeek(
            Calendar.getInstance().apply { time = date }
        )
        return if (Calendar.getInstance().time.compareWithoutTime(date) &&
            trainingTypeMock.trainingDays.contains(dayOfWeek)
        ) {
            val dayOnStart =
                (trainingTypeMock.trainingStartDate.time - date.time).toDuration(DurationUnit.DAYS)
                    .toInt(DurationUnit.DAYS)
            val trainingDayNumber =
                trainingTypeMock.trainingDays.indexOf(dayOfWeek) + dayOnStart /
                    DayOfWeekCalendarAdapter.values().size * trainingTypeMock.trainingDays.size

            trainingTypeMock.trainingPrograms.get(trainingDayNumber % trainingTypeMock.trainingPrograms.size)
        } else {
            EmptyProgram()
        }
    }
}
