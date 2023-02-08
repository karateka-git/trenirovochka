package com.example.trenirovochka.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.trenirovochka.R
import com.example.trenirovochka.data.local.models.ActionWithDate
import com.example.trenirovochka.databinding.FragmentHomeBinding
import com.example.trenirovochka.databinding.ViewHolderExerciseBinding
import com.example.trenirovochka.domain.models.*
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.dialogs.SimpleDialog
import com.example.trenirovochka.presentation.common.dialogs.SimpleDialog.Companion.SIMPLE_DIALOG_TAG
import com.example.trenirovochka.presentation.common.dialogs.SimpleDialogListener
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
import com.example.trenirovochka.presentation.common.recycler.SimpleAdapter
import com.example.trenirovochka.presentation.common.recycler.decorations.VerticalDividerDecoration
import com.example.trenirovochka.presentation.screens.home.viewHolders.ExerciseViewHolder
import com.example.trenirovochka.presentation.screens.main.SharedCurrentTrainingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModelCreator()

    private val sharedCurrentTrainingViewModel: SharedCurrentTrainingViewModel by viewModelCreator(
        ::requireActivity
    )

    private val trainingProgramAdapter by lazy {
        SimpleAdapter(
            ViewHolderExerciseBinding::inflate
        ) { ExerciseViewHolder(it) }
    }

    private val dialog by lazy {
        SimpleDialog(
            requireContext().getString(R.string.training_cancel_dialog_title),
            object : SimpleDialogListener {
                override fun onPositiveButtonClick() {
                    sharedCurrentTrainingViewModel.cancelTrainingProgram()
                }

                override fun onNegativeButtonClick() {}

                override fun onDismissClick() {
                    viewModel.isCancelActiveTrainingProgramDialogShow.postValue(false)
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclers()
        initObservers()
        initListeners()
    }

    private fun initRecyclers() {
        binding.trainingProgramRecycler.apply {
            adapter = trainingProgramAdapter
            addItemDecoration(
                VerticalDividerDecoration(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.vertical_divider
                    )
                )
            )
        }
    }

    private fun initObservers() {
        binding.apply {
            viewModel.apply {
                trainingPlan.observe(viewLifecycleOwner) {
                    if (it != null) {
                        updateTrainingPlan(it)
                    }
                }
                trainingProgram.observe(viewLifecycleOwner) {
                    updateTrainingProgram(it)
                }
                selectedDate.observe(viewLifecycleOwner) {
                    datePickerSelectedDateText.text = it
                }
                isCancelActiveTrainingProgramDialogShow.observe(viewLifecycleOwner) {
                    if (it) showDialog()
                }
            }
            sharedCurrentTrainingViewModel.trainingProgram.observe(viewLifecycleOwner) {
                handleActiveTrainingProgram(it)
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            trainingPlanEditIcon.setOnClickListener {
                viewModel.onEditTrainingPlanClick()
            }
            datePickerForwardButton.setOnClickListener {
                viewModel.updateSelectedDate(ActionWithDate.NEXT)
            }
            datePickerBackButton.setOnClickListener {
                viewModel.updateSelectedDate(ActionWithDate.PREV)
            }
            startTrainingButton.setOnClickListener {
                viewModel.trainingProgram.value?.let {
                    sharedCurrentTrainingViewModel.updateCurrentTrainingProgram(
                        TrainingProgram(
                            it as TrainingProgram
                        )
                    )
                    viewModel.onStartTrainingButtonClick()
                }
            }
            cancelTrainingButton.setOnClickListener {
                viewModel.onCancelTrainingButtonClick()
            }
            continueTrainingButton.setOnClickListener {
                viewModel.onContinueTrainingButtonClick(
                    sharedCurrentTrainingViewModel.trainingProgram.value
                )
            }
        }
    }

    private fun updateTrainingPlan(plan: TrainingPlan) {
        binding.trainingPlanName.text = plan.name
    }

    private fun updateTrainingProgram(program: Program) {
        binding.apply {
            trainingButtonContainer.visibleChildId = when (program) {
                is PerformedTrainingProgram -> {
                    performedTrainingText.id
                }
                is TrainingProgram -> {
                    startTrainingButton.id
                }
                is EmptyProgram -> {
                    emptyTrainingDayText.id
                }
            }
        }
        trainingProgramAdapter.swapItems(program.exercises)
    }

    private fun handleActiveTrainingProgram(activeTrainingProgram: TrainingProgram?) {
        binding.actionButtonContainer.visibleChildId = if (activeTrainingProgram == null) {
            binding.trainingButtonContainer.id
        } else {
            binding.activeTrainingButtonContainer.id
        }
    }

    private fun showDialog() {
        dialog.show(childFragmentManager, SIMPLE_DIALOG_TAG)
    }
}
