package com.example.trenirovochka.presentation.screens.editTrainingProgram

import android.os.Bundle
import android.view.View
import com.example.trenirovochka.databinding.FragmentEditTrainingProgramBinding
import com.example.trenirovochka.databinding.ViewHolderEditExerciseBinding
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.dialogs.EditExerciseDialog
import com.example.trenirovochka.presentation.common.dialogs.EditExerciseDialog.Companion.EDIT_EXERCISE_DIALOG_TAG
import com.example.trenirovochka.presentation.common.dialogs.EditExerciseDialogListener
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
import com.example.trenirovochka.presentation.common.recycler.SimpleAdapter
import com.example.trenirovochka.presentation.screens.editTrainingPlan.models.EditTrainingProgram
import com.example.trenirovochka.presentation.screens.editTrainingPlan.models.toEditExercise
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
            EditExerciseViewHolder(it) {
                viewModel.onEditExerciseSelectClick(it)
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
        }
    }

    private fun initListeners() {
        binding.apply {
            toolbar.setBackButtonOnClickListener {
                viewModel.exit()
            }
            addExerciseToTrainingProgramButton.setOnClickListener {
                EditExerciseDialog(
                    listener = object : EditExerciseDialogListener {
                        override fun onPositiveButtonClick(exercise: Exercise) {
                            viewModel.addExerciseToTrainingProgram(exercise.toEditExercise())
                        }

                        override fun onNegativeButtonClick() {}
                    }
                ).show(childFragmentManager, EDIT_EXERCISE_DIALOG_TAG)
            }
        }
    }

    private fun updateTrainingProgram(trainingProgram: EditTrainingProgram) {
        binding.apply {
            exerciseAdapter.swapItems(trainingProgram.exercise)
            trainingProgramNameEditText.setText(trainingProgram.name)
        }
    }
}
