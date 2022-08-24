package com.example.trenirovochka.domain.models

import android.content.Context
import android.os.Parcelable
import com.example.trenirovochka.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrainingProgram(
    val date: String,
    val name: String,
    val exercise: List<Exercise>,
    val active: Boolean = false,
) : Parcelable {

    fun hasActiveExercise(): Boolean {
        return exercise.any { it.status }
    }
}

@Parcelize
data class Exercise(
    val name: String,
    val numberOfTotalSets: Int,
    var numberOfCompletedSets: Int = 0,
    val numberOfRepetitions: Int,
    val usedWeight: String,
    val description: String? = null,
    var status: Boolean = false, // TODO do enum ExecutionStatus(NotStarted, InProgress, InPause, Completed)
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
}
