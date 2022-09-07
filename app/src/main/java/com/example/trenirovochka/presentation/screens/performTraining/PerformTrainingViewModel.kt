package com.example.trenirovochka.presentation.screens.performTraining

import androidx.lifecycle.*
import com.example.trenirovochka.domain.extensions.formatAsTime
import com.example.trenirovochka.domain.extensions.parseTime
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.domain.models.TrainingProgram.Companion.ExecutionStatus
import com.example.trenirovochka.domain.models.TrainingProgram.Companion.ExecutionStatus.*
import com.example.trenirovochka.domain.models.UserStatus
import com.example.trenirovochka.domain.models.UserStatus.InPause.Companion.getRecoveryLevel
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import com.example.trenirovochka.presentation.common.navigation.HomeDestination
import com.example.trenirovochka.presentation.common.util.CountTimer
import com.example.trenirovochka.presentation.common.util.CountTimer.Companion.DEFAULT_START_VALUE
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
    val trainingProgramVM: LiveData<TrainingProgram> = _trainingProgramVM.map {
        it.also { program -> programStatusChanged(program.status) }
    }
    private val _timeTraining: MutableLiveData<Duration> = MutableLiveData(timeForRelax)
    val timeTraining: LiveData<String> = _timeTraining.map { formatAsTime(it) }

    private val _userState: MutableLiveData<UserStatus> = MutableLiveData(UserStatus.NotStarted)
    val userState: LiveData<UserStatus> = _userState.distinctUntilChanged()

    fun updateExercises(item: Exercise) {
        trainingProgram.exercise.forEach {
            if (it == item || it.isStatusInProgress()) {
                it.updateExecutionStatus()
            }
        }
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

    private fun programStatusChanged(exerciseStatus: ExecutionStatus) {
        when (exerciseStatus) {
            NOT_STARTED -> {}
            IN_PROGRESS -> {
                updateCountTimer(TimerState.TIMER_UP, DEFAULT_START_VALUE)
            }
            IN_PAUSE -> {
                updateCountTimer(TimerState.TIMER_DOWN, timeForRelax)
            }
            COMPLETED -> {}
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
        updateUserState(time)
    }

    private fun updateUserState(time: Duration) {
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
