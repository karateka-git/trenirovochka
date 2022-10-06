package com.example.trenirovochka.data.local.storage.entities

import androidx.room.*

data class TrainingProgramJoinEntity(
    @Embedded
    val trainingProgram: TrainingProgramEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "programId"
    )
    val exercises: List<TrainingExerciseEntity>,
)

@Entity(
    tableName = "trainingProgram",
    foreignKeys = [
        ForeignKey(
            entity = TrainingPlanEntity::class,
            parentColumns = ["id"],
            childColumns = ["planId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class TrainingProgramEntity(
    @PrimaryKey val id: Long,
    val planId: Long,
    val name: String,
)
