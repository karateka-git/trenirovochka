package com.example.trenirovochka.domain.navigation

import androidx.annotation.IdRes
import com.example.trenirovochka.R

sealed class RouteSection(@IdRes val graph: Int) {
    object Home : RouteSection(R.id.main_graph)
}

sealed class RouteDestination(@IdRes val destination: Int) {

    sealed class Home(@IdRes destination: Int) : RouteDestination(destination) {
        object HomeScreen : Home(R.id.homeFragment)
        object PerformTrainingScreen : Home(R.id.performTrainingFragment)
    }
}
