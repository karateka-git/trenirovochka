package com.example.trenirovochka.presentation.screens.editTrainingPlan

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.trenirovochka.databinding.FragmentEditTrainingPlanBinding
import com.example.trenirovochka.databinding.ViewHolderTrainingDayBinding
import com.example.trenirovochka.domain.extensions.formatAsFullDate
import com.example.trenirovochka.domain.models.Training
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
import com.example.trenirovochka.presentation.common.recycler.SimpleAdapter
import com.example.trenirovochka.presentation.screens.editTrainingPlan.viewHolders.TrainingDayViewHolder
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
            args.trainingPlan
        )
    }

    private val trainingDays by lazy {
        SimpleAdapter(
            ViewHolderTrainingDayBinding::inflate
        ) {
            TrainingDayViewHolder(it) { item ->
                viewModel.updateSelectedTrainingDays(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclers()
        initObservers()
    }

    private fun initRecyclers() {
        binding.trainingDays.adapter = trainingDays
    }

    private fun initObservers() {
        viewModel.apply {
            trainingPlanVM.observe(viewLifecycleOwner) {
                updateTrainingPlan(it)
            }
        }
    }

    private fun updateTrainingPlan(trainingPlan: Training) {
        trainingDays.swapItems(trainingPlan.trainingDays)
        binding.apply {
            toolbar.setBackButtonOnClickListener {
                viewModel.exit()
            }
            trainingPlanNameEditText.setText(trainingPlan.name)
            trainingPlanFirstDayText.setText(formatAsFullDate(trainingPlan.trainingStartDate))
        }
    }
}