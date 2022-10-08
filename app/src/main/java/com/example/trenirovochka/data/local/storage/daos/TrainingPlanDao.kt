package com.example.trenirovochka.data.local.storage.daos

import androidx.room.*
import com.example.trenirovochka.data.local.storage.entities.TrainingExerciseEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingPlanEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingPlanJoinEntity
import com.example.trenirovochka.data.local.storage.entities.TrainingProgramEntity
import kotlinx.coroutines.flow.Flow

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingPlanEntity: TrainingPlanEntity)

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
