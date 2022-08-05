package com.example.trenirovochka.presentation.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val programsInteractor: ITrainingProgramsInteractor
) : ViewModel() {

    val trainingProgram = programsInteractor.getTrainingProgram("").asLiveData()
}
