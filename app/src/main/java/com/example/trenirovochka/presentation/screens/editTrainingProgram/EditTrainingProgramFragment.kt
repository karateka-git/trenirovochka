package com.example.trenirovochka.presentation.screens.editTrainingProgram

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.example.trenirovochka.databinding.FragmentEditTrainingProgramBinding
import com.example.trenirovochka.databinding.ViewHolderEditExerciseBinding
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.dialogs.EditExerciseDialog
import com.example.trenirovochka.presentation.common.dialogs.EditExerciseDialog.Companion.EDIT_EXERCISE_DIALOG_TAG
import com.example.trenirovochka.presentation.common.dialogs.EditExerciseDialogListener
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
import com.example.trenirovochka.presentation.common.recycler.SimpleAdapter
import com.example.trenirovochka.presentation.screens.editTrainingProgram.models.EditExercise
import com.example.trenirovochka.presentation.screens.editTrainingProgram.models.EditTrainingProgram
import com.example.trenirovochka.presentation.screens.editTrainingProgram.viewHolders.EditExerciseViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTrainingProgramFragment : BaseFragment<FragmentEditTrainingProgramBinding, EditTrainingProgramViewModel>(
    FragmentEditTrainingProgramBinding::inflate
) {

    override val viewModel: EditTrainingProgramViewModel by viewModelCreator()

    private val exerciseAdapter by lazy {
        SimpleAdapter(
            ViewHolderEditExerciseBinding::inflate
        ) {
            EditExerciseViewHolder(it) { exercise ->
                viewModel.selectExercise(exercise)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObservers()
        initListeners()
    }

    private fun initRecycler() {
        binding.trainingProgramExercisesRecycler.adapter = exerciseAdapter
    }

    private fun initObservers() {
        viewModel.apply {
            trainingProgram.observe(viewLifecycleOwner) {
                updateTrainingProgram(it)
            }
            selectedExercise.observe(viewLifecycleOwner) {
                updateBottomContainer(it != null)
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            toolbar.setBackButtonOnClickListener {
                viewModel.exit()
            }
            addExerciseToTrainingProgramButton.setOnClickListener {
                showEditExerciseDialog(viewModel.selectedExercise.value) {
                    viewModel.addExerciseToTrainingProgram(it)
                }
            }
            trainingProgramNameEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onTrainingProgramNameChanged(text.toString())
            }
            editExerciseButton.setOnClickListener {
                showEditExerciseDialog(viewModel.selectedExercise.value) {
                    viewModel.editSelectedExercise(it)
                }
            }
            removeExerciseButton.setOnClickListener {
                viewModel.removeSelectedExercise()
            }
        }
    }

    private fun updateTrainingProgram(trainingProgram: EditTrainingProgram) {
        binding.apply {
            exerciseAdapter.swapItems(trainingProgram.exercises)
            trainingProgramNameEditText.setText(trainingProgram.name)
        }
    }

    private fun updateBottomContainer(isSelectedExercise: Boolean) {
        binding.apply {
            bottomContainerViewAnimator.visibleChildId =
                if (isSelectedExercise) {
                    editExerciseContainer.id
                } else {
                    addExerciseToTrainingProgramButton.id
                }
        }
    }

    private fun showEditExerciseDialog(exercise: EditExercise?, onPositiveButtonClick: (exercise: EditExercise) -> Unit) {
        EditExerciseDialog(
            exercise = exercise,
            listener = object : EditExerciseDialogListener {
                override fun onPositiveButtonClick(exercise: EditExercise) {
                    onPositiveButtonClick(exercise)
                }

                override fun onNegativeButtonClick() {}
            }
        ).show(childFragmentManager, EDIT_EXERCISE_DIALOG_TAG)
    }
}
