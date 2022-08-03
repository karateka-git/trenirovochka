package com.example.trenirovochka.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val programsInteractor: ITrainingProgramsInteractor
) : ViewModel() {

    val trainingProgram = programsInteractor.getTrainingProgram("").asLiveData()
}
