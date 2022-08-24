package com.example.trenirovochka.presentation.screens.performTraining

import androidx.lifecycle.*
import com.example.trenirovochka.domain.extensions.formatAsTime
import com.example.trenirovochka.domain.extensions.parseTime
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import com.example.trenirovochka.presentation.common.navigation.HomeDestination
import com.example.trenirovochka.presentation.common.util.CountTimer
import com.example.trenirovochka.presentation.common.util.CountTimer.Companion.TimerState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@AssistedFactory
interface PerformTrainingViewModelAssistedFactory {
    fun create(trainingProgram: TrainingProgram): PerformTrainingViewModel
}

class PerformTrainingViewModel @AssistedInject constructor(
    @Assisted private val trainingProgram: TrainingProgram,
    private val countTimer: CountTimer,
) : BaseViewModel<HomeDestination>() {

    companion object {
        private val DEFAULT_START_VALUE = 0.toDuration(DurationUnit.SECONDS)

        enum class RecoveryLevel(val partTime: Int) {
            NONE(0),
            HIGH(4),
            MEDIUM(2),
            LOW(1),
        }
    }

    init {
        countTimer.setTimerListener {
            _timeTraining.value = it
            countTimerChanged(it)
        }
    }

    private var timeForRelax: Duration = 2.toDuration(DurationUnit.MINUTES)
        set(value) {
            field = value
            countTimer.setTime(value)
        }

    private val _trainingProgramVM: MutableLiveData<TrainingProgram> =
        MutableLiveData(trainingProgram)
    val trainingProgramVM: LiveData<TrainingProgram> = _trainingProgramVM
    private val _timeTraining: MutableLiveData<Duration> = MutableLiveData(timeForRelax)
    val timeTraining: LiveData<String> = _timeTraining.map { formatAsTime(it) }
    private val _isActiveExerciseStatus: MutableLiveData<Boolean> = MutableLiveData()
    val isActiveExerciseStatus: LiveData<Boolean> = _isActiveExerciseStatus.map {
        it.also { status -> isActiveExerciseStatusChanged(status) }
    }
    private val _recoveryLevelState: MutableLiveData<RecoveryLevel> = MutableLiveData(RecoveryLevel.NONE)
    val recoveryLevelState: LiveData<RecoveryLevel> = _recoveryLevelState.distinctUntilChanged()

    fun updateExercises(item: Exercise) {
        trainingProgram.exercise.forEach {
            if (it == item) {
                it.updateExecutionStatus()
            } else if (it.isStatusInProgress()) {
                it.updateExecutionStatus()
            }
        }
        _isActiveExerciseStatus.value = item.isStatusInProgress()
        _trainingProgramVM.value = trainingProgram
    }

    fun changeTimeForRelax(time: String) {
        parseTime(time)?.let {
            timeForRelax = it
        }
    }

    fun onTimerFocusChange(isFocusState: Boolean) {
        if (isFocusState) {
            countTimer.pause()
        } else {
            countTimer.resume()
        }
    }

    private fun isActiveExerciseStatusChanged(exerciseStatus: Boolean) {
        when (exerciseStatus) {
            true -> {
                updateCountTimer(TimerState.TIMER_UP, DEFAULT_START_VALUE)
            }
            else -> {
                updateCountTimer(TimerState.TIMER_DOWN, timeForRelax)
            }
        }
    }

    private fun updateCountTimer(state: TimerState, time: Duration) {
        countTimer.apply {
            setTime(time)
            setState(state)
            startTimer(viewModelScope)
        }
    }

    private fun countTimerChanged(time: Duration) {
        if (time <= DEFAULT_START_VALUE) countTimer.cancelTimer()
        if (isActiveExerciseStatus.value == false) {
            updateRecoveryLevelState(time)
        } else {
            _recoveryLevelState.value = RecoveryLevel.NONE
        }
    }

    private fun updateRecoveryLevelState(time: Duration) {
        _recoveryLevelState.value = if (time > timeForRelax.div(RecoveryLevel.MEDIUM.partTime)) {
            RecoveryLevel.LOW
        } else if (time > timeForRelax.div(RecoveryLevel.HIGH.partTime)) {
            RecoveryLevel.MEDIUM
        } else {
            RecoveryLevel.HIGH
        }
    }
}
