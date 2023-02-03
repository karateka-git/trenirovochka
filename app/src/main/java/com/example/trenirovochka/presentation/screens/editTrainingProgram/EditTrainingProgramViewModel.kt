package com.example.trenirovochka.presentation.screens.editTrainingProgram

import androidx.lifecycle.*
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import com.example.trenirovochka.presentation.screens.editTrainingProgram.models.EditExercise
import com.example.trenirovochka.presentation.screens.editTrainingProgram.models.EditTrainingProgram
import com.example.trenirovochka.presentation.screens.editTrainingProgram.models.toEditTrainingProgram
import com.example.trenirovochka.presentation.screens.editTrainingProgram.models.toTrainingProgram
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTrainingProgramViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val programsInteractor: ITrainingProgramsInteractor,
) : BaseViewModel() {

    private val args: EditTrainingProgramFragmentArgs by lazy {
        EditTrainingProgramFragmentArgs.fromSavedStateHandle(savedStateHandle)
    }

    private val _trainingProgramLD: MutableLiveData<EditTrainingProgram> = MutableLiveData()
    val trainingProgramLD: LiveData<EditTrainingProgram> = MediatorLiveData<EditTrainingProgram>().apply {
        addSource(programsInteractor.getTrainingProgram(args.trainingProgramId).asLiveData()) {
            value = it.toEditTrainingProgram()
        }
        addSource(_trainingProgramLD) {
            value = it
        }
    }

    val selectedExercise: LiveData<EditExercise?> = trainingProgramLD.map { program ->
        program.exercises.find { it.isSelected }
    }

    override fun exit() {
        viewModelScope.launch {
            updateDatabaseTrainingProgram()
            super.exit()
        }
    }

    fun onTrainingProgramNameChanged(text: String) {
        trainingProgramLD.value?.let { currentTrainingProgram ->
            if (currentTrainingProgram.name != text) {
                viewModelScope.launch {
                    _trainingProgramLD.postValue(
                        currentTrainingProgram.copy(name = text)
                    )
                }
            }
        }
    }

    fun selectExercise(selectedExercise: EditExercise) {
        _trainingProgramLD.value = trainingProgramLD.value?.apply {
            exercises = exercises.toMutableList().apply {
                forEachIndexed { index, editExercise ->
                    if (editExercise == selectedExercise) {
                        set(index, editExercise.copy(isSelected = editExercise.isSelected.not()))
                    } else if (editExercise.isSelected) {
                        set(index, editExercise.copy(isSelected = false))
                    }
                }
            }
        }
    }

    fun addExerciseToTrainingProgram(newExercise: EditExercise) {
        trainingProgramLD.value?.let { currentTrainingProgram ->
            viewModelScope.launch {
                _trainingProgramLD.postValue(
                    currentTrainingProgram.copy(
                        exercises = currentTrainingProgram.exercises.toMutableList()
                            .apply { add(newExercise) }
                    )
                )
            }
        }
    }

    fun removeSelectedExercise() {
        trainingProgramLD.value?.let { currentTrainingProgram ->
            viewModelScope.launch {
                _trainingProgramLD.postValue(
                    currentTrainingProgram.copy(
                        exercises = currentTrainingProgram.exercises.toMutableList()
                            .apply { remove(selectedExercise.value) }
                    )
                )
            }
        }
    }

    // TODO mb change
    fun editSelectedExercise(editExercise: EditExercise) {
        trainingProgramLD.value?.let { currentTrainingProgram ->
            viewModelScope.launch {
                _trainingProgramLD.postValue(
                    currentTrainingProgram.copy(
                        exercises = currentTrainingProgram.exercises.toMutableList()
                            .apply {
                                forEachIndexed { index, eachExercise ->
                                    if (eachExercise == selectedExercise.value) {
                                        set(index, editExercise)
                                    }
                                }
                            }
                    )
                )
            }
        }
    }

    private suspend fun updateDatabaseTrainingProgram() {
        trainingProgramLD.value?.let { currentTrainingProgram ->
            programsInteractor.updateTrainingProgram(
                args.trainingPlanId,
                currentTrainingProgram.toTrainingProgram()
            )
        }
    }
}
