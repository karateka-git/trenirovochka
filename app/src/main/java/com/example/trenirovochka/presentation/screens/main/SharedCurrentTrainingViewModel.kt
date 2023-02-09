package com.example.trenirovochka.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.trenirovochka.domain.extensions.parseTime
import com.example.trenirovochka.domain.interactors.interfaces.ICompletedTrainingProgramsInteractor
import com.example.trenirovochka.domain.models.CompletedTrainingProgram
import com.example.trenirovochka.domain.models.ExecutionStatus
import com.example.trenirovochka.domain.models.ExecutionStatus.*
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import com.example.trenirovochka.presentation.common.util.CountTimer
import com.example.trenirovochka.presentation.common.util.CountTimer.Companion.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class SharedCurrentTrainingViewModel @Inject constructor(
    private val completedProgramsInteractor: ICompletedTrainingProgramsInteractor,
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
    val trainingProgram: LiveData<TrainingProgram?> = _trainingProgram.map {
        it?.apply {
            programStatusChanged(status)
        }
    }

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

    fun updateExercises(item: Exercise) {
        trainingProgram.value?.let { trainingProgram ->
            val newExercise = trainingProgram.exercises.toMutableList()
            newExercise.forEachIndexed { index, it ->
                if (it == item || it.isStatusInProgress()) {
                    newExercise[index] = it.copy().apply { updateExecutionStatus() }
                }
            }
            _trainingProgram.value = trainingProgram.copy(
                exercises = newExercise
            )
        }
    }

    fun cancelTrainingProgram() {
        viewModelScope.launch {
            trainingProgram.value?.let {
                completedProgramsInteractor.add(
                    CompletedTrainingProgram(
                        it.id,
                        it.name,
                        Calendar.getInstance().time,
                        it.exercises,
                        it.status
                    )
                )
            }
        }
        resetTimer()
        updateCurrentTrainingProgram(null)
    }

    private fun programStatusChanged(exerciseStatus: ExecutionStatus) {
        when (exerciseStatus) {
            NOT_STARTED -> {}
            IN_PROGRESS -> {
                updateCountTimer(TimerState.TIMER_UP, CountTimer.DEFAULT_START_VALUE)
            }
            IN_PAUSE -> {
                updateCountTimer(TimerState.TIMER_DOWN, timeForRelax)
            }
            COMPLETED -> {}
        }
    }

    private fun updateCountTimer(state: TimerState, time: Duration) {
        countTimer.apply {
            cancelTimer()
            setTime(time)
            setState(state)
            startTimer(viewModelScope)
        }
    }

    private fun resetTimer() {
        countTimer.cancelTimer()
        _timeTraining.value = timeForRelax
    }
}
