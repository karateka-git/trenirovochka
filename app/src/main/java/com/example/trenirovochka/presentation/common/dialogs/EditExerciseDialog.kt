package com.example.trenirovochka.presentation.common.dialogs

import com.example.trenirovochka.databinding.DialogEditExerciseBinding
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.presentation.common.base.BaseDialogActionListener
import com.example.trenirovochka.presentation.common.base.BaseDialogFragment
import com.example.trenirovochka.presentation.screens.editTrainingPlan.models.EditExercise

class EditExerciseDialog(
    private val exercise: EditExercise? = null,
    override val listener: EditExerciseDialogListener,
) : BaseDialogFragment<EditExerciseDialogListener, DialogEditExerciseBinding>(
    DialogEditExerciseBinding::inflate
) {

    companion object {
        const val EDIT_EXERCISE_DIALOG_TAG = "EDIT_EXERCISE_DIALOG_TAG"
    }

    override fun initViews() {
        fillExerciseInfo()
        binding.apply {
            positiveButton.setOnClickListener {
                listener.onPositiveButtonClick(getNewExercise())
                this@EditExerciseDialog.dismiss()
            }
            negativeButton.setOnClickListener {
                listener.onNegativeButtonClick()
                this@EditExerciseDialog.dismiss()
            }
        }
    }

    private fun getNewExercise(): EditExercise {
        binding.apply {
            return EditExercise(
                name = exerciseNameEditText.text.toString(),
                numberOfTotalSets = exerciseTotalSetsEditText.text.toString().toInt(),
                numberOfRepetitions = exerciseTotalRepetitionsEditText.text.toString().toInt(),
                usedWeight = exerciseWeightEditText.text.toString(),
                description = exerciseDescriptionEditText.text.toString()
            )
        }
    }

    private fun fillExerciseInfo() {
        exercise?.let {
            binding.apply {
                exerciseNameEditText.setText(it.name)
                exerciseTotalSetsEditText.setText(it.numberOfTotalSets.toString())
                exerciseTotalRepetitionsEditText.setText(it.numberOfRepetitions.toString())
                exerciseWeightEditText.setText(it.usedWeight)
                exerciseDescriptionEditText.setText(it.description)
            }
        }
    }
}

interface EditExerciseDialogListener : BaseDialogActionListener {
    fun onPositiveButtonClick(exercise: EditExercise)
    fun onNegativeButtonClick()
}
