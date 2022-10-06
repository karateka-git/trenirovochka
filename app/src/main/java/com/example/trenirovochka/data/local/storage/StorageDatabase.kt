package com.example.trenirovochka.data.local.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trenirovochka.data.local.storage.daos.TrainingPlanDao
import com.example.trenirovochka.data.local.storage.entities.TrainingExerciseEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingPlanEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingProgramEntity

@Database(
    entities = [
        TrainingPlanEntity::class,
        TrainingProgramEntity::class,
        TrainingExerciseEntity::class,
    ],
    version = 1
)
abstract class StorageDatabase : RoomDatabase() {
    abstract fun trainingPlanDao(): TrainingPlanDao
}
