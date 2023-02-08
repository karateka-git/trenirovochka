package com.example.trenirovochka.presentation.screens.performTraining

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.example.trenirovochka.domain.models.ExecutionStatus
import com.example.trenirovochka.domain.models.ExecutionStatus.*
import com.example.trenirovochka.domain.models.UserStatus
import com.example.trenirovochka.domain.models.UserStatus.InPause.Companion.getRecoveryLevel
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import javax.inject.Inject
import kotlin.time.Duration

class PerformTrainingViewModel @Inject constructor() : BaseViewModel() {

    private val _userState: MutableLiveData<UserStatus> = MutableLiveData(UserStatus.NotStarted)
    val userState: LiveData<UserStatus> = _userState.distinctUntilChanged()

    fun updateUserState(time: Duration, timeForRelax: Duration, status: ExecutionStatus) {
        _userState.value = when (status) {
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
