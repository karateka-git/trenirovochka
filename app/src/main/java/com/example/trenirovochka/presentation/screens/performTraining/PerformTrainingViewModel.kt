package com.example.trenirovochka.presentation.screens.performTraining

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trenirovochka.domain.models.Exercise
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

    private val _trainingProgramVM: MutableLiveData<TrainingProgram> = MutableLiveData(trainingProgram)
    val trainingProgramVM: LiveData<TrainingProgram> = _trainingProgramVM

    fun updateExerciseStatus(item: Exercise) {
        item.status = !item.status // TODO change
        _trainingProgramVM.postValue(trainingProgram)
    }
}
