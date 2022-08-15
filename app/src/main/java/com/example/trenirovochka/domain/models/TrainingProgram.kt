package com.example.trenirovochka.domain.models

import android.content.Context
import android.os.Parcelable
import com.example.trenirovochka.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrainingProgram(
    val date: String,
    val name: String,
    val exercise: List<Exercise>
) : Parcelable

@Parcelize
data class Exercise(
    val name: String,
    val numberOfSets: Int,
    val numberOfRepetitions: Int,
    val usedWeight: String,
    val description: String? = null,
) : Parcelable {
    fun getExecutionDescription(context: Context): String =
        context.getString(
            R.string.execution_description,
            numberOfSets,
            numberOfRepetitions,
            usedWeight
        )
}
