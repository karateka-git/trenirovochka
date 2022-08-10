package com.example.trenirovochka.domain.models

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
)
