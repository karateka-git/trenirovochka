package com.example.trenirovochka.presentation.screens.editTrainingPlan.models

import android.content.Context
import com.example.trenirovochka.R
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.domain.models.Program
import com.example.trenirovochka.domain.models.TrainingProgram

data class EditTrainingProgram(
    val id: String,
    var name: String,
    var exercises: List<EditExercise>,
)

data class EditExercise(
    val id: Long = 0, // default value for autogenerate
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

fun Program.toEditTrainingProgram(): EditTrainingProgram {
    return EditTrainingProgram(
        id,
        name,
        exercises.map { it.toEditExercise() }.toMutableList(),
    )
}

fun Exercise.toEditExercise(): EditExercise {
    return EditExercise(
        id,
        name,
        numberOfTotalSets,
        numberOfCompletedSets,
        numberOfRepetitions,
        usedWeight,
        description
    )
}

fun EditTrainingProgram.toTrainingProgram(): TrainingProgram {
    return TrainingProgram(
        id,
        name,
        exercises.map { it.toExercise() }
    )
}

fun EditExercise.toExercise(): Exercise {
    return Exercise(
        id,
        name,
        numberOfTotalSets,
        numberOfCompletedSets,
        numberOfRepetitions,
        usedWeight,
        description
    )
}
