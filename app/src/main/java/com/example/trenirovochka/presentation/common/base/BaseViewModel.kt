package com.example.trenirovochka.presentation.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.trenirovochka.domain.common.SingleEvent

abstract class BaseViewModel : ViewModel() {
    private val _navigationEvent: MutableLiveData<SingleEvent<NavController.() -> Any>> = MutableLiveData()
    val navigationEvent: LiveData<SingleEvent<NavController.() -> Any>> = _navigationEvent

    fun navigateTo(direction: NavDirections) {
        withNavController { navigate(direction) }
    }

    fun exit() {
        withNavController { navigateUp() }
    }

    protected fun withNavController(block: NavController.() -> Any) {
        _navigationEvent.postValue(SingleEvent(block))
    }
}
