package com.example.trenirovochka.domain.models

import android.content.Context
import android.os.Parcelable
import com.example.trenirovochka.R
import com.example.trenirovochka.domain.models.ExecutionStatus.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrainingPlan(
    val id: Long,
    val name: String,
    val trainingDays: List<TrainingDay>,
    val trainingPrograms: List<TrainingProgram>
) : Parcelable

@Parcelize
data class TrainingDay(
    val dayOfWeek: DaysOfWeek,
    val isSelected: Boolean = false
) : Parcelable

@Parcelize
sealed class Program : Parcelable {
    abstract val id: Long
    abstract val name: String
    abstract val exercises: List<Exercise>
}

data class TrainingProgram(
    override val id: Long,
    override val name: String,
    override val exercises: List<Exercise>,
) : Program() {

    constructor(program: TrainingProgram) : this(
        program.id,
        program.name,
        program.exercises.map { it.copy() },
    )

    val status: ExecutionStatus
        get() = if (exercises.any { it.status == IN_PROGRESS }) {
            IN_PROGRESS
        } else if (exercises.any { it.status == IN_PAUSE }) {
            IN_PAUSE
        } else if (exercises.all { it.status == COMPLETED }) {
            COMPLETED
        } else {
            NOT_STARTED
        }
}

data class PerformedTrainingProgram(
    override val id: Long,
    val date: String,
    override val name: String,
    override val exercises: List<Exercise>,
    val status: ExecutionStatus = COMPLETED
) : Program()

data class EmptyProgram(
    override val id: Long = 0,
    override val name: String = "пустая программа",
    override val exercises: List<Exercise> = listOf()
) : Program()

@Parcelize
data class Exercise(
    val id: Long = 0, // default value for autogenerate
    val name: String,
    val numberOfTotalSets: Int,
    var numberOfCompletedSets: Int = 0,
    val numberOfRepetitions: Int,
    val usedWeight: String,
    val description: String? = null,
    var status: ExecutionStatus = NOT_STARTED,
) : Parcelable {

    fun getExecutionDescription(context: Context): String =
        context.getString(
            R.string.execution_description,
            numberOfTotalSets,
            numberOfRepetitions,
            usedWeight
        )

    fun getActiveExecutionDescription(context: Context): String =
        context.getString(
            R.string.active_execution_description,
            usedWeight,
            numberOfCompletedSets,
            numberOfTotalSets
        )

    private fun addCompletedSet() {
        numberOfCompletedSets += 1
    }

    fun updateExecutionStatus() {
        status = when (status) {
            NOT_STARTED -> IN_PROGRESS
            IN_PROGRESS -> {
                addCompletedSet()
                if (numberOfCompletedSets < numberOfTotalSets) {
                    IN_PAUSE
                } else {
                    COMPLETED
                }
            }
            IN_PAUSE -> IN_PROGRESS
            COMPLETED -> IN_PROGRESS
        }
    }

    fun isStatusInProgress(): Boolean {
        return status == IN_PROGRESS
    }
}

enum class ExecutionStatus {
    NOT_STARTED,
    IN_PROGRESS,
    IN_PAUSE,
    COMPLETED,
}
