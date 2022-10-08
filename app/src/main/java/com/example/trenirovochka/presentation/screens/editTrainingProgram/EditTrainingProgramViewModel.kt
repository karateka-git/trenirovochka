package com.example.trenirovochka.presentation.screens.editTrainingProgram

import androidx.lifecycle.*
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import com.example.trenirovochka.presentation.screens.editTrainingPlan.models.EditExercise
import com.example.trenirovochka.presentation.screens.editTrainingPlan.models.EditTrainingProgram
import com.example.trenirovochka.presentation.screens.editTrainingPlan.models.toEditTrainingProgram
import com.example.trenirovochka.presentation.screens.editTrainingPlan.models.toTrainingProgram
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditTrainingProgramViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val programsInteractor: ITrainingProgramsInteractor,
) : BaseViewModel() {

    private val args: EditTrainingProgramFragmentArgs by lazy {
        EditTrainingProgramFragmentArgs.fromSavedStateHandle(savedStateHandle)
    }

    private val _trainingProgram: MutableLiveData<EditTrainingProgram> = getTrainingProgram(args.trainingProgramId)
    val trainingProgram: LiveData<EditTrainingProgram> = _trainingProgram
    val selectedExercise: LiveData<EditExercise?> = _trainingProgram.map { program ->
        program.exercises.find { it.isSelected }
    }

    fun onEditExerciseSelectClick(selectedExercise: EditExercise) {
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
        _trainingProgram.value = _trainingProgram.value?.apply {
            exercises = exercises.toMutableList().apply {
                add(newExercise)
            }
            programsInteractor.updateTrainingProgram(this.toTrainingProgram())
        }
    }

    fun removeExerciseFromTrainingProgram(selectedExercise: EditExercise?) {
        selectedExercise?.let {
            _trainingProgram.value = _trainingProgram.value?.apply {
                exercises = exercises.toMutableList().apply {
                    remove(selectedExercise)
                }
            }
        }
    }

    fun editExerciseToTrainingProgram(oldExercise: EditExercise?, editExercise: EditExercise) {
        _trainingProgram.value = _trainingProgram.value?.apply {
            exercises = exercises.toMutableList().apply {
                forEachIndexed { index, eachExercise ->
                    if (eachExercise == oldExercise) {
                        set(index, editExercise)
                    }
                }
            }
        }
    }

    private fun getTrainingProgram(id: String): MutableLiveData<EditTrainingProgram> {
        return programsInteractor.getTrainingProgram(id).asLiveData().map { it.toEditTrainingProgram() } as MutableLiveData<EditTrainingProgram>
    }
}
