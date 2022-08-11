package com.example.trenirovochka.domain.models

import android.content.Context
import com.example.trenirovochka.R

data class TrainingProgram(
    val date: String,
    val name: String,
    val exercise: List<Exercise>
)

data class Exercise(
    val name: String,
    val numberOfSets: Int,
    val numberOfRepetitions: Int,
    val usedWeight: String,
    val description: String? = null,
) {
    fun getExecutionDescription(context: Context): String =
        context.getString(
            R.string.execution_description,
            numberOfSets,
            numberOfRepetitions,
            usedWeight
        )
}
