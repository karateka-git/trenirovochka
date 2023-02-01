package com.example.trenirovochka.domain.interactors

import com.example.trenirovochka.domain.datacontracts.ITrainingProgramRemoteRepository
import com.example.trenirovochka.domain.datacontracts.storage.ITrainingPlanStorageRepository
import com.example.trenirovochka.domain.extensions.*
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import com.example.trenirovochka.domain.models.DaysOfWeek
import com.example.trenirovochka.domain.models.EmptyProgram
import com.example.trenirovochka.domain.models.Program
import com.example.trenirovochka.domain.models.TrainingPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class TrainingProgramsInteractor @Inject constructor(
    private val trainingProgramRemoteRepository: ITrainingProgramRemoteRepository,
    private val trainingPlanStorageRepository: ITrainingPlanStorageRepository,
) : ITrainingProgramsInteractor {

    override suspend fun updateTrainingPlan(newTrainingPlan: TrainingPlan) {
        trainingPlanStorageRepository.savePlan(newTrainingPlan.toTrainingPlanJoinEntity())
    }

    override fun getTrainingPlan(): Flow<TrainingPlan> {
        return trainingPlanStorageRepository.getSelectedTrainingPlan().map { it.toTrainingPlanDomain() }
    }

    override fun getTrainingProgram(date: Date): Flow<Program> =
        trainingPlanStorageRepository.getSelectedTrainingPlan().map {
            getTrainingProgramFromTrainingPlan(date, it.toTrainingPlanDomain())
        }

    override fun getTrainingProgram(id: Long): Flow<Program> =
        trainingPlanStorageRepository.getSelectedTrainingPlan().map { trainingPlan ->
            trainingPlan.trainingPrograms.find {
                it.trainingProgram.id == id
            }?.toTrainingProgramDomain() ?: EmptyProgram()
        }

    override suspend fun updateTrainingProgram(planId: Long, program: Program) {
        trainingPlanStorageRepository.updateTrainingProgram(program.toTrainingProgramJoinEntity(planId))
    }

    private fun getTrainingProgramFromTrainingPlan(date: Date, trainingPlan: TrainingPlan?): Program {
        if (trainingPlan == null) return EmptyProgram()

        val currentDay = Calendar.getInstance().time
        val dayOfWeek = DaysOfWeek.getDayOfWeek(
            Calendar.getInstance().apply { time = date }
        )
        val selectedTrainingDays = trainingPlan.trainingDays.filter { it.isSelected }.map { it.dayOfWeek }

        return if (currentDay.beforeOrEqualWithoutTime(date) &&
            trainingPlan.trainingPrograms.size >= selectedTrainingDays.size &&
            selectedTrainingDays.contains(dayOfWeek)
        ) {
            trainingPlan.trainingPrograms[
                selectedTrainingDays.indexOf(dayOfWeek) % trainingPlan.trainingPrograms.size
            ]
        } else {
            EmptyProgram()
        }
    }
}
