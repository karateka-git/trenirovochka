package com.example.trenirovochka.data.local.storage.entities

import androidx.room.*
import com.example.trenirovochka.domain.models.ExecutionStatus

@Entity(
    tableName = "trainingExercise",
    foreignKeys = [
        ForeignKey(
            entity = TrainingProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class TrainingExerciseEntity(
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

class ExecutionStatusConverter {

    @TypeConverter
    fun fromExecutionStatus(value: ExecutionStatus) = value.name

    @TypeConverter
    fun toExecutionStatus(value: String) = enumValueOf<ExecutionStatus>(value)
}
