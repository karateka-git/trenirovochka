package com.example.trenirovochka.domain.extensions

import com.example.trenirovochka.data.local.storage.entities.*
import com.example.trenirovochka.domain.models.*

fun TrainingPlanJoinEntity.toTrainingPlanDomain() =
    TrainingPlan(
        trainingPlan.id,
        trainingPlan.name,
        trainingPlan.trainingDays,
        trainingPrograms.map { it.toTrainingProgramDomain() }
    )

fun TrainingProgramJoinEntity.toTrainingProgramDomain() =
    TrainingProgram(
        trainingProgram.id,
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

fun Program.toTrainingProgramJoinEntity(planId: Long) =
    TrainingProgramJoinEntity(
        TrainingProgramEntity(
            id,
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

fun CompletedTrainingProgram.toCompletedTrainingProgramJoinEntity() =
    CompletedTrainingProgramJoinEntity(
        CompletedTrainingProgramEntity(
            id,
            name,
            date,
            status
        ),
        exercises.map { it.toCompletedExerciseEntity(id) }
    )

fun Exercise.toCompletedExerciseEntity(programId: Long) =
    CompletedTrainingExerciseEntity(
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

fun CompletedTrainingProgramJoinEntity.toCompletedTrainingProgramDomain() =
    CompletedTrainingProgram(
        trainingProgram.id,
        trainingProgram.name,
        trainingProgram.date,
        exercises.map { it.toCompletedExerciseDomain() },
        trainingProgram.status,
    )

fun CompletedTrainingExerciseEntity.toCompletedExerciseDomain() =
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
