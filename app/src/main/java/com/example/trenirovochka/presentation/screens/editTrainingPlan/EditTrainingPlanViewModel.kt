package com.example.trenirovochka.presentation.screens.editTrainingPlan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trenirovochka.domain.models.TrainingPlan
import com.example.trenirovochka.domain.models.TrainingDay
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@AssistedFactory
interface EditTrainingPlanViewModelAssistedFactory {
    fun create(trainingPlan: TrainingPlan): EditTrainingPlanViewModel
}

class EditTrainingPlanViewModel @AssistedInject constructor(
    @Assisted private val trainingPlan: TrainingPlan,
) : BaseViewModel() {

    private val _trainingPlanVM: MutableLiveData<TrainingPlan> = MutableLiveData(trainingPlan)
    val trainingPlanVM: LiveData<TrainingPlan> = _trainingPlanVM

    fun updateSelectedTrainingDays(trainingDay: TrainingDay) {
        trainingPlan.trainingDays = trainingPlan.trainingDays.toMutableList().let { list ->
            list.apply {
                set(list.indexOf(trainingDay), trainingDay.copy().apply { isSelected = !isSelected })
            }
        }
        _trainingPlanVM.value = trainingPlan
    }

    fun onTrainingPlanNameChanged(text: String) {
        if (trainingPlan.name != text) trainingPlan.apply { name = text }
    }

    fun onTrainingProgramClick(trainingProgram: TrainingProgram) {
        navigateTo(EditTrainingPlanFragmentDirections.actionEditTrainingPlanFragmentToEditTrainingProgramFragment())
    }
}