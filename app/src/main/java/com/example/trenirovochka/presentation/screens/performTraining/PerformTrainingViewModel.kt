package com.example.trenirovochka.presentation.screens.performTraining

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.domain.models.TrainingProgram.Companion.ExecutionStatus.*
import com.example.trenirovochka.domain.models.UserStatus
import com.example.trenirovochka.domain.models.UserStatus.InPause.Companion.getRecoveryLevel
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlin.time.Duration

@AssistedFactory
interface PerformTrainingViewModelAssistedFactory {
    fun create(trainingProgram: TrainingProgram): PerformTrainingViewModel
}

class PerformTrainingViewModel @AssistedInject constructor(
    @Assisted private val trainingProgram: TrainingProgram,
) : BaseViewModel() {

    private val _trainingProgramVM: MutableLiveData<TrainingProgram> =
        MutableLiveData(trainingProgram)
    val trainingProgramVM: LiveData<TrainingProgram> = _trainingProgramVM

    private val _userState: MutableLiveData<UserStatus> = MutableLiveData(UserStatus.NotStarted)
    val userState: LiveData<UserStatus> = _userState.distinctUntilChanged()

    fun updateExercises(item: Exercise) {
        val newExercise = trainingProgram.exercises.toMutableList()
        newExercise.forEachIndexed { index, it ->
            if (it == item || it.isStatusInProgress()) {
                newExercise[index] = it.copy().apply { updateExecutionStatus() }
            }
        }
        _trainingProgramVM.value = trainingProgram.apply {
            exercises = newExercise
        }
    }

    fun updateUserState(time: Duration, timeForRelax: Duration) {
        _userState.value = when (trainingProgram.status) {
            NOT_STARTED -> { UserStatus.NotStarted }
            IN_PROGRESS -> {
                UserStatus.InProgress
            }
            IN_PAUSE -> {
                UserStatus.InPause(getRecoveryLevel(time, timeForRelax))
            }
            COMPLETED -> { UserStatus.Completed }
        }
    }
}
