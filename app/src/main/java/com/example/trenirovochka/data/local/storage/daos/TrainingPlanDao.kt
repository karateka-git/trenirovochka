package com.example.trenirovochka.data.local.storage.daos

import androidx.room.*
import com.example.trenirovochka.data.local.storage.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingPlanDao {
    @Transaction
    suspend fun insert(trainingPlanEntity: TrainingPlanJoinEntity) {
        insert(trainingPlanEntity.trainingPlan)
        trainingPlanEntity.trainingPrograms.forEach {
            insert(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingPlanEntity: TrainingPlanEntity)

    @Transaction
    suspend fun insert(trainingProgramJoinEntity: TrainingProgramJoinEntity) {
        insert(trainingProgramJoinEntity.trainingProgram)
        trainingProgramJoinEntity.exercises.forEach {
            insert(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingProgramEntity: TrainingProgramEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainingExerciseEntity: TrainingExerciseEntity)

    @Transaction
    @Query("SELECT * FROM trainingPlan")
    suspend fun getAll(): List<TrainingPlanJoinEntity>

    @Transaction
    @Query("SELECT * FROM trainingPlan LIMIT 1") // TODO change for selected training plan
    fun getSelectedTrainingPlan(): Flow<TrainingPlanJoinEntity>
}
