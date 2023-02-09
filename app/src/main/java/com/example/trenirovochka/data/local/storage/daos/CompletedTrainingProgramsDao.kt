package com.example.trenirovochka.data.local.storage.daos

import androidx.room.*
import com.example.trenirovochka.data.local.storage.entities.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CompletedTrainingProgramsDao {

    @Transaction
    suspend fun insert(programJoinEntity: CompletedTrainingProgramJoinEntity) {
        insert(programJoinEntity.trainingProgram)
        programJoinEntity.exercises.forEach {
            insert(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programEntity: CompletedTrainingProgramEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(exerciseEntity: CompletedTrainingExerciseEntity)

    @Transaction
    @Query("SELECT * FROM completedTrainingProgram")
    suspend fun getAll(): List<CompletedTrainingProgramJoinEntity>

    @Transaction
    @Query("SELECT * FROM completedTrainingProgram WHERE date == :date LIMIT 1")
    fun get(date: Date): Flow<CompletedTrainingProgramJoinEntity?>
}
