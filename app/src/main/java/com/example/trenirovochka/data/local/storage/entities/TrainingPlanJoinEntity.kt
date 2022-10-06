package com.example.trenirovochka.data.local.storage.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class TrainingPlanJoinEntity(
    @Embedded
    val trainingPlan: TrainingPlanEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "planId",
        entity = TrainingProgramEntity::class
    )
    val trainingPrograms: List<TrainingProgramJoinEntity>,
)

@Entity(tableName = "trainingPlan")
data class TrainingPlanEntity(
    @PrimaryKey val id: Long,
    val name: String,
)
