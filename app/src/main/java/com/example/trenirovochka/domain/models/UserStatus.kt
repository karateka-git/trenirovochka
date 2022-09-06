package com.example.trenirovochka.domain.models

import kotlin.time.Duration

sealed class UserStatus {
    object NotStarted : UserStatus()
    object InProgress : UserStatus()
    class InPause(val recoveryLevel: RecoveryLevel = RecoveryLevel.HIGH) : UserStatus() {
        companion object {
            fun getRecoveryLevel(time: Duration, timeForRelax: Duration) =
                if (time > timeForRelax.div(RecoveryLevel.MEDIUM.partTime)) {
                    RecoveryLevel.LOW
                } else if (time > timeForRelax.div(RecoveryLevel.HIGH.partTime)) {
                    RecoveryLevel.MEDIUM
                } else {
                    RecoveryLevel.HIGH
                }
        }
    }
    object Completed : UserStatus()
}

enum class RecoveryLevel(val partTime: Int) {
    NONE(0),
    HIGH(4),
    MEDIUM(2),
    LOW(1),
}
