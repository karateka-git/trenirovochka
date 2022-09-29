package com.example.trenirovochka.presentation.common.dialogs

import com.example.trenirovochka.databinding.DialogEditExerciseBinding
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.presentation.common.base.BaseDialogActionListener
import com.example.trenirovochka.presentation.common.base.BaseDialogFragment

class EditExerciseDialog(
    private val exercise: Exercise? = null,
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

    private fun getNewExercise(): Exercise {
        binding.apply {
            return Exercise(
                exerciseNameEditText.text.toString(),
                exerciseTotalSetsEditText.text.toString().toInt(),
                exerciseTotalRepetitionsEditText.text.toString().toInt(),
                exerciseWeightEditText.text.toString().toInt(),
                exerciseDescriptionEditText.text.toString()
            )
        }
    }

    private fun fillExerciseInfo() {
        exercise?.let {
            // TODO
        }
    }
}

interface EditExerciseDialogListener : BaseDialogActionListener {
    fun onPositiveButtonClick(exercise: Exercise)
    fun onNegativeButtonClick()
}
