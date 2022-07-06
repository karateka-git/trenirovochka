package com.example.trenirovochka.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.trenirovochka.data.remote.repositories.TrainingProgramRemoteRepositoryMock
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import com.example.trenirovochka.domain.interactors.interfaces.TrainingProgramsInteractor

class MainViewModel : ViewModel() {
    var programsInteractor: ITrainingProgramsInteractor =
        TrainingProgramsInteractor(TrainingProgramRemoteRepositoryMock())

    val trainingProgram = programsInteractor.getTrainingProgram("").asLiveData()
}
