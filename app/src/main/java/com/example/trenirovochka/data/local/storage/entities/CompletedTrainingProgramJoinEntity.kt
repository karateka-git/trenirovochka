package com.example.trenirovochka.data.local.storage.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.trenirovochka.domain.models.ExecutionStatus
import java.util.*

data class CompletedTrainingProgramJoinEntity(
    @Embedded
    val trainingProgram: CompletedTrainingProgramEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "programId"
    )
    val exercises: List<CompletedTrainingExerciseEntity>,
)

@Entity(
    tableName = "completedTrainingProgram",
)
data class CompletedTrainingProgramEntity(
    @PrimaryKey val id: Long,
//    val planId: Long, TODO подумать надо ли (пока прихожу к выводу, что нет)
    val name: String,
    val date: Date,
    val status: ExecutionStatus
)
