package com.example.trenirovochka.data.local.storage.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.trenirovochka.data.local.storage.entities.TrainingExerciseEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingPlanEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingPlanJoinEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingProgramEntity

@Dao
interface TrainingPlanDao {
    @Transaction
    suspend fun insert(trainingPlanEntity: TrainingPlanJoinEntity) {
        insert(trainingPlanEntity.trainingPlan)
        trainingPlanEntity.trainingPrograms.forEach {
            insert(it.trainingProgram)
            it.exercises.forEach {
                insert(it)
            }
        }
    }

    @Insert
    fun insert(trainingExerciseEntity: TrainingExerciseEntity)

    @Insert
    suspend fun insert(trainingPlanEntity: TrainingPlanEntity)

    @Insert
    suspend fun insert(trainingProgramEntity: TrainingProgramEntity)

    @Transaction
    @Query("SELECT * FROM trainingPlan")
    suspend fun getAll(): List<TrainingPlanJoinEntity>
}
