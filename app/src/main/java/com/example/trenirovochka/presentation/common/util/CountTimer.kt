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
    }

    private var timer: Job? = null
    private var timeDuration: Duration = 0.toDuration(DurationUnit.SECONDS)

    private fun startCoroutineTimer(
        scope: CoroutineScope,
        action: () -> Unit
    ) = scope.launch(Dispatchers.IO) {
        while (true) {
            action()
            delay(DEFAULT_VALUE_TIMER_CHANGE_IN_SECOND.inWholeMilliseconds)
        }
    }

    fun setTime(time: Duration) {
        timeDuration = time
    }

    fun startTimer(
        state: TimerState,
        scope: CoroutineScope,
        action: (Duration) -> Unit,
    ) {
        cancelTimer()
        timer = startCoroutineTimer(scope) {
            timeDuration = actionWithTime(timeDuration, state)
            scope.launch(Dispatchers.Main) {
                action(timeDuration)
            }
        }.also {
            it.start()
        }
    }

    fun cancelTimer() {
        timer?.cancel()
    }

    private fun actionWithTime(time: Duration, state: TimerState): Duration {
        return when (state) {
            TimerState.TIMER_UP -> time.plus(DEFAULT_VALUE_TIMER_CHANGE_IN_SECOND)
            TimerState.TIMER_DOWN -> time.minus(DEFAULT_VALUE_TIMER_CHANGE_IN_SECOND)
        }
    }
}
