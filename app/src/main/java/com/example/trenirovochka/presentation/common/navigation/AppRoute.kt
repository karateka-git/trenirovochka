package com.example.trenirovochka.presentation.common.navigation

import androidx.annotation.IdRes
import com.example.trenirovochka.R

sealed class RouteSection<T : RouteDestination>(
    @IdRes val graph: Int,
    private val destinations: List<T>
) {
    object HomeSection : RouteSection<HomeDestination>(
        R.id.main_graph,
        HomeDestination.values().toList()
    )
}

enum class HomeDestination(@IdRes override val destination: Int) : RouteDestination {
    HomeScreen(R.id.homeFragment),
    PerformTrainingScreen(R.id.performTrainingFragment)
}

interface RouteDestination {
    val destination: Int
}
