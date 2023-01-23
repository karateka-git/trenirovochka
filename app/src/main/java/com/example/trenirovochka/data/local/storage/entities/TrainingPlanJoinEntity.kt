package com.example.trenirovochka.data.local.storage.entities

import androidx.room.*
import com.example.trenirovochka.domain.models.TrainingDay
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
@TypeConverters(TrainingDaysConverter::class)
data class TrainingPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val trainingDays: List<TrainingDay>,
)

class TrainingDaysConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromJson(jsonString: String): List<TrainingDay> {
        return if (jsonString.isEmpty()) {
            emptyList()
        } else {
            gson.fromJson(
                jsonString,
                object : TypeToken<List<TrainingDay>>() {}.type
            )
        }
    }

    @TypeConverter
    fun toJson(value: List<TrainingDay>): String = gson.toJson(value)
}
