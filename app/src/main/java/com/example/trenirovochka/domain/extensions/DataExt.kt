package com.example.trenirovochka.domain.extensions

import com.example.trenirovochka.data.local.storage.entities.*
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.domain.models.TrainingPlan
import com.example.trenirovochka.domain.models.TrainingProgram

fun TrainingPlanJoinEntity.toTrainingPlanDomain() =
    TrainingPlan(
        trainingPlan.id,
        trainingPlan.name,
        trainingPlan.trainingDays,
        trainingPrograms.map { it.toTrainingProgramDomain() }
    )

fun TrainingProgramJoinEntity.toTrainingProgramDomain() =
    TrainingProgram(
        trainingProgram.id.toString(),
        trainingProgram.name,
        exercises.map {
            it.toTrainingExerciseDomain()
        }
    )

fun TrainingExerciseEntity.toTrainingExerciseDomain() =
    Exercise(
        id,
        name,
        numberOfTotalSets,
        numberOfCompletedSets,
        numberOfRepetitions,
        usedWeight,
        description,
        status
    )

fun TrainingPlan.toTrainingPlanJoinEntity() =
    TrainingPlanJoinEntity(
        TrainingPlanEntity(
            id,
            name,
            trainingDays
        ),
        trainingPrograms.map { it.toTrainingProgramJoinEntity(id) }
    )

fun TrainingProgram.toTrainingProgramJoinEntity(planId: Long) =
    TrainingProgramJoinEntity(
        TrainingProgramEntity(
            id.toLong(),
            planId,
            name
        ),
        exercises.map { it.toTrainingExerciseEntity(id.toLong()) }
    )

fun Exercise.toTrainingExerciseEntity(programId: Long) =
    TrainingExerciseEntity(
        id,
        programId,
        name,
        numberOfTotalSets,
        numberOfCompletedSets,
        numberOfRepetitions,
        usedWeight,
        description,
        status
    )
