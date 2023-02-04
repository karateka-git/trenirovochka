package com.example.trenirovochka.presentation.screens.editTrainingPlan

import androidx.lifecycle.*
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

    private var _trainingPlanLD: MutableLiveData<TrainingPlan> = MutableLiveData()
    val trainingPlanLD: LiveData<TrainingPlan> = MediatorLiveData<TrainingPlan>().apply {
        addSource(programsInteractor.getTrainingPlan().asLiveData()) {
            value = it
        }
        addSource(_trainingPlanLD) {
            value = it
        }
    }

    override fun exit() {
        viewModelScope.launch {
            updateDatabaseTrainingPlan()
            super.exit()
        }
    }

    fun updateSelectedTrainingDays(trainingDay: TrainingDay) {
        trainingPlanLD.value?.let { currentTrainingPlan ->
            viewModelScope.launch {
                _trainingPlanLD.postValue(
                    currentTrainingPlan.copy(
                        trainingDays = currentTrainingPlan.trainingDays.toMutableList().let { list ->
                            list.apply {
                                set(
                                    list.indexOf(trainingDay),
                                    trainingDay.copy(
                                        isSelected = trainingDay.isSelected.not()
                                    )
                                )
                            }
                        }
                    )
                )
            }
        }
    }

    fun onTrainingPlanNameChanged(text: String) {
        trainingPlanLD.value?.let { currentTrainingPlan ->
            if (currentTrainingPlan.name != text) {
                viewModelScope.launch {
                    _trainingPlanLD.postValue(
                        currentTrainingPlan.copy(name = text)
                    )
                }
            }
        }
    }

    fun onTrainingProgramClick(trainingProgram: TrainingProgram) {
        viewModelScope.launch {
            updateDatabaseTrainingPlan()
            navigateTo(
                EditTrainingPlanFragmentDirections.actionEditTrainingPlanFragmentToEditTrainingProgramFragment(
                    trainingPlanId
                ).apply {
                    trainingProgramId = trainingProgram.id
                }
            )
        }
    }

    fun addTrainingProgram() {
        viewModelScope.launch {
            updateDatabaseTrainingPlan()
            navigateTo(EditTrainingPlanFragmentDirections.actionEditTrainingPlanFragmentToEditTrainingProgramFragment(trainingPlanId))
        }
    }

    private suspend fun updateDatabaseTrainingPlan() {
        trainingPlanLD.value?.let { currentTrainingPlan ->
            programsInteractor.updateTrainingPlan(currentTrainingPlan)
        }
    }
}
