package com.example.trenirovochka.presentation.screens.editTrainingPlan.models

import android.content.Context
import com.example.trenirovochka.R
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.domain.models.TrainingProgram

data class EditTrainingProgram(
    var name: String,
    var exercise: List<EditExercise>,
)

data class EditExercise(
    val name: String,
    val numberOfTotalSets: Int,
    var numberOfCompletedSets: Int = 0,
    val numberOfRepetitions: Int,
    val usedWeight: String,
    val description: String? = null,
    var isSelected: Boolean = false,
) {

    fun getExecutionDescription(context: Context): String =
        context.getString(
            R.string.execution_description,
            numberOfTotalSets,
            numberOfRepetitions,
            usedWeight
        )
}

fun TrainingProgram.toEditTrainingProgram(): EditTrainingProgram {
    return EditTrainingProgram(
        name,
        exercise.map { it.toEditExercise() }.toMutableList(),
    )
}

fun Exercise.toEditExercise(): EditExercise {
    return EditExercise(
        name,
        numberOfTotalSets,
        numberOfCompletedSets,
        numberOfRepetitions,
        usedWeight,
        description
    )
}
