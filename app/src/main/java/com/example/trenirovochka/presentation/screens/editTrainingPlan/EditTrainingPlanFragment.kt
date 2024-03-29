package com.example.trenirovochka.presentation.screens.editTrainingPlan

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.trenirovochka.databinding.FragmentEditTrainingPlanBinding
import com.example.trenirovochka.databinding.ViewHolderTrainingDayBinding
import com.example.trenirovochka.databinding.ViewHolderTrainingProgramBinding
import com.example.trenirovochka.domain.models.TrainingPlan
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.extensions.addKeyDoneListener
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
import com.example.trenirovochka.presentation.common.recycler.SimpleAdapter
import com.example.trenirovochka.presentation.screens.editTrainingPlan.viewHolders.TrainingDayViewHolder
import com.example.trenirovochka.presentation.screens.editTrainingPlan.viewHolders.TrainingProgramViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditTrainingPlanFragment : BaseFragment<FragmentEditTrainingPlanBinding, EditTrainingPlanViewModel>(
    FragmentEditTrainingPlanBinding::inflate
) {
    private val args by navArgs<EditTrainingPlanFragmentArgs>()

    @Inject
    lateinit var assistedFactory: EditTrainingPlanViewModelAssistedFactory

    override val viewModel: EditTrainingPlanViewModel by viewModelCreator {
        assistedFactory.create(
            args.trainingPlanId
        )
    }

    private val trainingDaysAdapter by lazy {
        SimpleAdapter(
            ViewHolderTrainingDayBinding::inflate
        ) {
            TrainingDayViewHolder(it) { item ->
                viewModel.updateSelectedTrainingDays(item)
            }
        }
    }

    private val trainingProgramsAdapter by lazy {
        SimpleAdapter(
            ViewHolderTrainingProgramBinding::inflate
        ) {
            TrainingProgramViewHolder(it) { trainingProgram ->
                viewModel.onTrainingProgramClick(trainingProgram)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclers()
        initObservers()
        initListeners()
    }

    override fun keyboardIsVisibleChanged(isVisible: Boolean) {
        if (isVisible.not()) {
            viewModel.onTrainingPlanNameChanged(
                binding.trainingPlanNameEditText.text.toString()
            )
        }
    }

    private fun initRecyclers() {
        binding.apply {
            trainingDays.adapter = trainingDaysAdapter
            trainingPlanProgramsRecycler.adapter = trainingProgramsAdapter
        }
    }

    private fun initObservers() {
        viewModel.apply {
            trainingPlanLD.observe(viewLifecycleOwner) {
                updateTrainingPlan(it)
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            trainingPlanNameEditText.addKeyDoneListener {
                viewModel.onTrainingPlanNameChanged(trainingPlanNameEditText.text.toString())
            }
            toolbar.setBackButtonOnClickListener {
                viewModel.exit()
            }
            addTrainingProgramContainerButton.setOnClickListener {
                viewModel.addTrainingProgram()
            }
        }
    }

    private fun updateTrainingPlan(trainingPlan: TrainingPlan) {
        trainingDaysAdapter.swapItems(trainingPlan.trainingDays)
        trainingProgramsAdapter.swapItems(trainingPlan.trainingPrograms)
        binding.trainingPlanNameEditText.apply {
            if (trainingPlan.name != text.toString()) {
                setText(trainingPlan.name)
                setSelection(trainingPlan.name.length)
            }
        }
    }
}
