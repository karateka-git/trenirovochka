package com.example.trenirovochka.presentation.screens.performTraining

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
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
    }

    init {
        countTimer.setTimerListener {
            _timeTraining.value = it
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

    fun updateExerciseStatus(item: Exercise) {
        trainingProgram.exercise.forEach {
            if (it == item) {
                it.status = !it.status // TODO change
            } else {
                it.status = false
            }
        }
        when (trainingProgram.hasActiveExercise()) {
            true -> updateCountTimer(TimerState.TIMER_UP, DEFAULT_START_VALUE)
            false -> updateCountTimer(TimerState.TIMER_DOWN, timeForRelax)
        }
        _trainingProgramVM.value = trainingProgram
    }

    fun changeTimeForRelax(time: String) {
        parseTime(time)?.let {
            timeForRelax = it
        }
    }

    private fun updateCountTimer(state: TimerState, time: Duration) {
        countTimer.apply {
            setTime(time)
            setState(state)
            startTimer(viewModelScope)
        }
    }

    fun onTimerFocusChange(isFocusState: Boolean) {
        if (isFocusState) {
            countTimer.cancelTimer()
        } else {
            countTimer.startTimer(viewModelScope)
        }
    }
}
