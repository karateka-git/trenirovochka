package com.example.trenirovochka.presentation.screens.editTrainingPlan

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import com.example.trenirovochka.domain.models.TrainingDay
import com.example.trenirovochka.domain.models.TrainingPlan
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

@AssistedFactory
interface EditTrainingPlanViewModelAssistedFactory {
    fun create(trainingPlanId: Long): EditTrainingPlanViewModel
}

class EditTrainingPlanViewModel @AssistedInject constructor(
    @Assisted private val trainingPlanId: Long, // TODO use this id
    private val programsInteractor: ITrainingProgramsInteractor,
) : BaseViewModel() {

    val trainingPlanVM: LiveData<TrainingPlan> = programsInteractor.getTrainingPlan().asLiveData()

    fun updateSelectedTrainingDays(trainingDay: TrainingDay) {
        trainingPlanVM.value?.let { currentTrainingPlan ->
            viewModelScope.launch {
                programsInteractor.updateTrainingPlan(
                    currentTrainingPlan.copy(
                        trainingDays = currentTrainingPlan.trainingDays.toMutableList().let { list ->
                            list.apply {
                                set(list.indexOf(trainingDay), trainingDay.copy().apply { isSelected = !isSelected })
                            }
                        }
                    )
                )
            }
        }
    }

    fun onTrainingPlanNameChanged(text: String) {
        trainingPlanVM.value?.let { currentTrainingPlan ->
            if (currentTrainingPlan.name != text) {
                viewModelScope.launch {
                    programsInteractor.updateTrainingPlan(
                        currentTrainingPlan.copy(name = text)
                    )
                }
            }
        }
    }

    fun onTrainingProgramClick(trainingProgram: TrainingProgram) {
        navigateTo(
            EditTrainingPlanFragmentDirections.actionEditTrainingPlanFragmentToEditTrainingProgramFragment(
                trainingPlanId
            ).apply {
                trainingProgramId = trainingProgram.id
            }
        )
    }

    fun addTrainingProgram() {
        navigateTo(EditTrainingPlanFragmentDirections.actionEditTrainingPlanFragmentToEditTrainingProgramFragment(trainingPlanId))
    }
}
