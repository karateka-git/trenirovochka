package com.example.trenirovochka.domain.models

import android.content.Context
import android.os.Parcelable
import com.example.trenirovochka.R
import com.example.trenirovochka.domain.models.TrainingProgram.Companion.ExecutionStatus
import com.example.trenirovochka.domain.models.TrainingProgram.Companion.ExecutionStatus.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrainingPlan(
    var name: String,
    var trainingDays: List<TrainingDay>,
    val trainingPrograms: List<TrainingProgram>
) : Parcelable

@Parcelize
data class TrainingDay(
    val dayOfWeek: DaysOfWeek,
    var isSelected: Boolean = false
) : Parcelable

@Parcelize
sealed class Program : Parcelable {
    abstract val exercise: List<Exercise>
}

data class TrainingProgram(
    val name: String,
    override var exercise: List<Exercise>,
    val active: Boolean = false,
) : Program() {

    constructor(program: TrainingProgram) : this(
        program.name,
        program.exercise.map { it.copy() },
        program.active
    )

    companion object {
        enum class ExecutionStatus {
            NOT_STARTED,
            IN_PROGRESS,
            IN_PAUSE,
            COMPLETED,
        }
    }

    val status: ExecutionStatus
        get() = if (exercise.any { it.status == IN_PROGRESS }) {
            IN_PROGRESS
        } else if (exercise.any { it.status == IN_PAUSE }) {
            IN_PAUSE
        } else if (exercise.all { it.status == COMPLETED }) {
            COMPLETED
        } else {
            NOT_STARTED
        }
}

data class PerformedTrainingProgram(
    val date: String,
    val name: String,
    override val exercise: List<Exercise>,
    val status: ExecutionStatus = COMPLETED
) : Program()

data class EmptyProgram(
    override val exercise: List<Exercise> = listOf()
) : Program()

@Parcelize
data class Exercise(
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

    fun addCompletedSet() {
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
