package com.example.trenirovochka.presentation.common.util

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CountTimer {

    companion object {
        enum class TimerState {
            TIMER_UP,
            TIMER_DOWN,
        }

        val DEFAULT_VALUE_TIMER_CHANGE_IN_SECOND = 1.seconds
        val DEFAULT_START_VALUE = 0.toDuration(DurationUnit.SECONDS)
    }

    private var timer: Job? = null
    private var timeDuration: Duration = 0.toDuration(DurationUnit.SECONDS)
    private var timerState: TimerState = TimerState.TIMER_DOWN
    private var timerListener: ((Duration) -> Unit)? = null
    private var isPause: Boolean = false

    private fun startCoroutineTimer(
        scope: CoroutineScope,
        action: () -> Unit
    ) = scope.launch(Dispatchers.IO) {
        while (true) {
            if (isPause.not()) {
                action()
                delay(DEFAULT_VALUE_TIMER_CHANGE_IN_SECOND.inWholeMilliseconds)
            }
        }
    }

    fun setTime(time: Duration) {
        timeDuration = time
    }

    fun setState(state: TimerState) {
        timerState = state
    }

    fun setTimerListener(listener: (Duration) -> Unit) {
        timerListener = listener
    }

    fun startTimer(
        scope: CoroutineScope,
    ) {
        cancelTimer()
        timer = startCoroutineTimer(scope) {
            timeDuration = actionWithTime(timeDuration)
            scope.launch(Dispatchers.Main) {
                timerListener?.invoke(timeDuration)
            }
        }.also {
            it.start()
        }
    }

    fun cancelTimer() {
        timer?.cancel()
    }

    fun pause() {
        isPause = true
    }

    fun resume() {
        isPause = false
    }

    private fun actionWithTime(time: Duration): Duration {
        return when (timerState) {
            TimerState.TIMER_UP -> time.plus(DEFAULT_VALUE_TIMER_CHANGE_IN_SECOND)
            TimerState.TIMER_DOWN -> time.minus(DEFAULT_VALUE_TIMER_CHANGE_IN_SECOND)
        }
    }
}
