package com.example.trenirovochka.presentation.screens.performTraining

import androidx.lifecycle.MutableLiveData
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import com.example.trenirovochka.presentation.common.navigation.HomeDestination
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@AssistedFactory
interface PerformTrainingViewModelAssistedFactory {
    fun create(trainingProgram: TrainingProgram): PerformTrainingViewModel
}

class PerformTrainingViewModel @AssistedInject constructor(
    @Assisted private val trainingProgram: TrainingProgram
) : BaseViewModel<HomeDestination>() {

    val test: MutableLiveData<String> = MutableLiveData(trainingProgram.name)
}
