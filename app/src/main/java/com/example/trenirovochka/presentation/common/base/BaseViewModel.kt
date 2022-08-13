package com.example.trenirovochka.presentation.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.trenirovochka.domain.common.SingleEvent
import com.example.trenirovochka.domain.navigation.RouteDestination

abstract class BaseViewModel(
    // TODO
) : ViewModel() {
    private val _navigationEvent: MutableLiveData<SingleEvent<NavController.() -> Any>> = MutableLiveData()
    val navigationEvent: LiveData<SingleEvent<NavController.() -> Any>> = _navigationEvent

    fun navigateTo(route: RouteDestination) {
        withNavController { navigate(route.destination) }
    }

    protected fun withNavController(block: NavController.() -> Any) {
        _navigationEvent.postValue(SingleEvent(block))
    }
}
