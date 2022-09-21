package com.example.trenirovochka.presentation.screens.editTrainingPlan

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.trenirovochka.databinding.FragmentEditTrainingPlanBinding
import com.example.trenirovochka.domain.models.Training
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.apply {
            trainingPlanVM.observe(viewLifecycleOwner) {
                updateTrainingPlan(it)
            }
        }
    }

    private fun updateTrainingPlan(trainingPlan: Training) {
        binding.apply {
            toolbar.setBackButtonOnClickListener {
                viewModel.exit()
            }
        }
    }
}