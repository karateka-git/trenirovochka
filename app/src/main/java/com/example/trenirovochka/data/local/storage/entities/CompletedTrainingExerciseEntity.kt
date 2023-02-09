package com.example.trenirovochka.data.local.storage.entities

import androidx.room.*
import com.example.trenirovochka.data.local.storage.ExecutionStatusConverter
import com.example.trenirovochka.domain.models.ExecutionStatus

@Entity(
    tableName = "completedExercise",
    foreignKeys = [
        ForeignKey(
            entity = CompletedTrainingProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class CompletedTrainingExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val programId: Long,
    val name: String,
    val numberOfTotalSets: Int,
    val numberOfCompletedSets: Int = 0,
    val numberOfRepetitions: Int,
    val usedWeight: String,
    val description: String? = null,
    @TypeConverters(ExecutionStatusConverter::class)
    val status: ExecutionStatus = ExecutionStatus.NOT_STARTED,
)
