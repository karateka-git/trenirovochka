package com.example.trenirovochka.presentation.common.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.trenirovochka.domain.common.SingleEvent
import com.example.trenirovochka.presentation.common.navigation.RouteDestination

abstract class BaseViewModel<in D : RouteDestination> : ViewModel() {
    private val _navigationEvent: MutableLiveData<SingleEvent<NavController.() -> Any>> = MutableLiveData()
    val navigationEvent: LiveData<SingleEvent<NavController.() -> Any>> = _navigationEvent

    fun navigateTo(direction: NavDirections) {
        withNavController { navigate(direction) }
    }

    fun navigateTo(route: D, args: Bundle?) {
        withNavController { navigate(route.destination, args) }
    }

    fun backTo(route: D) {
        withNavController { popBackStack(route.destination, false) }
    }

    fun exit() {
        withNavController { navigateUp() }
    }

    protected fun withNavController(block: NavController.() -> Any) {
        _navigationEvent.postValue(SingleEvent(block))
    }
}
