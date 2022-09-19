package com.example.trenirovochka.presentation.screens.editTrainingPlan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trenirovochka.domain.models.Training
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@AssistedFactory
interface EditTrainingPlanViewModelAssistedFactory {
    fun create(trainingPlan: Training): EditTrainingPlanViewModel
}

class EditTrainingPlanViewModel @AssistedInject constructor(
    @Assisted private val trainingPlan: Training,
) : BaseViewModel() {

    private val _trainingPlanVM: MutableLiveData<Training> = MutableLiveData(trainingPlan)
    val trainingPlanVM: LiveData<Training> = _trainingPlanVM
}