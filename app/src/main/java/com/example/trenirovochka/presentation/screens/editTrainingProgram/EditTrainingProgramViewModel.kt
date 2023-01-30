package com.example.trenirovochka.presentation.screens.editTrainingProgram

import androidx.lifecycle.*
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import com.example.trenirovochka.domain.models.TrainingProgram
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

    private val _trainingProgram: MutableLiveData<EditTrainingProgram> =
        programsInteractor.getTrainingProgram(args.trainingProgramId).asLiveData().map {
            it.toEditTrainingProgram()
        } as MutableLiveData<EditTrainingProgram>
    val trainingProgram: LiveData<EditTrainingProgram> = _trainingProgram
    val selectedExercise: LiveData<EditExercise?> = _trainingProgram.map { program ->
        program.exercises.find { it.isSelected }
    }

    fun onTrainingProgramNameChanged(text: String) {
        _trainingProgram.value?.let { editTrainingProgram ->
            if (editTrainingProgram.name != text) {
                updateTrainingProgram(
                    editTrainingProgram.copy(name = text)
                )
            }
        }
    }

    fun selectExercise(selectedExercise: EditExercise) {
        _trainingProgram.value = _trainingProgram.value?.apply {
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
        _trainingProgram.value?.let { editTrainingProgram ->
            updateTrainingProgram(
                editTrainingProgram.copy(
                    exercises = editTrainingProgram.exercises.toMutableList()
                        .apply { add(newExercise) }
                )
            )
        }
    }

    fun removeSelectedExercise() {
        _trainingProgram.value?.let { editTrainingProgram ->
            updateTrainingProgram(
                editTrainingProgram.copy(
                    exercises = editTrainingProgram.exercises.toMutableList()
                        .apply { remove(selectedExercise.value) }
                )
            )
        }
    }

    fun editSelectedExercise(editExercise: EditExercise) {
        _trainingProgram.value?.let { editTrainingProgram ->
            updateTrainingProgram(
                editTrainingProgram.copy(
                    exercises = editTrainingProgram.exercises.toMutableList()
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

    private fun updateTrainingProgram(updatedTrainingProgram: EditTrainingProgram) {
        viewModelScope.launch {
            programsInteractor.updateTrainingProgram(
                args.trainingPlanId,
                updatedTrainingProgram.toTrainingProgram()
            )
        }
    }
}
