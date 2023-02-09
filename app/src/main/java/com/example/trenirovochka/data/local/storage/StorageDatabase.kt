package com.example.trenirovochka.data.local.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trenirovochka.data.local.storage.daos.CompletedTrainingProgramsDao
import com.example.trenirovochka.data.local.storage.daos.TrainingPlanDao
import com.example.trenirovochka.data.local.storage.entities.*

@Database(
    entities = [
        TrainingPlanEntity::class,
        TrainingProgramEntity::class,
        TrainingExerciseEntity::class,
        CompletedTrainingProgramEntity::class,
        CompletedTrainingExerciseEntity::class,
    ],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class StorageDatabase : RoomDatabase() {
    abstract fun trainingPlanDao(): TrainingPlanDao

    abstract fun completedTrainingProgramsDao(): CompletedTrainingProgramsDao
}
