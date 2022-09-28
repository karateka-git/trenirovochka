package com.example.trenirovochka.presentation.screens.editTrainingProgram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import com.example.trenirovochka.presentation.screens.editTrainingPlan.models.EditExercise
import com.example.trenirovochka.presentation.screens.editTrainingPlan.models.EditTrainingProgram
import com.example.trenirovochka.presentation.screens.editTrainingPlan.models.toEditTrainingProgram
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditTrainingProgramViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val args: EditTrainingProgramFragmentArgs by lazy {
        EditTrainingProgramFragmentArgs.fromSavedStateHandle(savedStateHandle)
    }

    private val _trainingProgram: MutableLiveData<EditTrainingProgram> = MutableLiveData(args.trainingProgram.toEditTrainingProgram())
    val trainingProgram: LiveData<EditTrainingProgram> = _trainingProgram

    fun onEditExerciseSelectClick(selectedExercise: EditExercise) {
        _trainingProgram.value = _trainingProgram.value?.apply {
            exercise = exercise.toMutableList().apply {
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
}
