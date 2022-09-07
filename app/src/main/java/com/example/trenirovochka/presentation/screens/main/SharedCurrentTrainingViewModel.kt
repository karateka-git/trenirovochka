package com.example.trenirovochka.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trenirovochka.domain.extensions.parseTime
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import com.example.trenirovochka.presentation.common.util.CountTimer
import com.example.trenirovochka.presentation.common.util.CountTimer.Companion.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class SharedCurrentTrainingViewModel @Inject constructor(
    private val countTimer: CountTimer,
) : BaseViewModel() {

    init {
        countTimer.setTimerListener {
            _timeTraining.value = it
            if (it <= CountTimer.DEFAULT_START_VALUE) countTimer.cancelTimer()
        }
    }

    private var timeForRelax: Duration = 2.toDuration(DurationUnit.MINUTES)
        set(value) {
            field = value
            countTimer.setTime(value)
        }

    private val _timeTraining: MutableLiveData<Duration> = MutableLiveData(timeForRelax)
    val timeTraining: LiveData<Duration> = _timeTraining

    private val _trainingProgram: MutableLiveData<TrainingProgram?> = MutableLiveData(null)
    val trainingProgram: LiveData<TrainingProgram?> = _trainingProgram

    fun updateCurrentTrainingProgram(program: TrainingProgram?) {
        _trainingProgram.value = program
    }

    fun changeTimeForRelax(time: String) {
        parseTime(time)?.let {
            timeForRelax = it
        }
    }

    fun getTimeForRelax(): Duration = timeForRelax

    fun onChangeTimerPauseState(isPause: Boolean) {
        if (isPause) {
            countTimer.pause()
        } else {
            countTimer.resume()
        }
    }

    fun programStatusChanged(exerciseStatus: TrainingProgram.Companion.ExecutionStatus) {
        when (exerciseStatus) {
            TrainingProgram.Companion.ExecutionStatus.NOT_STARTED -> {}
            TrainingProgram.Companion.ExecutionStatus.IN_PROGRESS -> {
                updateCountTimer(TimerState.TIMER_UP, CountTimer.DEFAULT_START_VALUE)
            }
            TrainingProgram.Companion.ExecutionStatus.IN_PAUSE -> {
                updateCountTimer(TimerState.TIMER_DOWN, timeForRelax)
            }
            TrainingProgram.Companion.ExecutionStatus.COMPLETED -> {}
        }
    }

    private fun updateCountTimer(state: TimerState, time: Duration) {
        countTimer.apply {
            setTime(time)
            setState(state)
            startTimer(viewModelScope)
        }
    }
}
