package com.example.trenirovochka.domain.models

import android.content.Context
import android.os.Parcelable
import com.example.trenirovochka.R
import com.example.trenirovochka.domain.models.Exercise.Companion.ExecutionStatus.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrainingProgram(
    val date: String,
    val name: String,
    val exercise: List<Exercise>,
    val active: Boolean = false,
) : Parcelable

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

    companion object {
        enum class ExecutionStatus {
            NOT_STARTED,
            IN_PROGRESS,
            IN_PAUSE,
            COMPLETED,
        }
    }

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
