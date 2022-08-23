package com.example.trenirovochka.presentation.screens.performTraining

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.trenirovochka.databinding.FragmentPerformTrainingBinding
import com.example.trenirovochka.databinding.ViewHolderActiveExerciseBinding
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.extensions.addMaskedChangeListener
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
import com.example.trenirovochka.presentation.common.recycler.SimpleAdapter
import com.example.trenirovochka.presentation.common.util.TextMask.TIME_SHORT_MASK
import com.example.trenirovochka.presentation.screens.performTraining.viewHolders.ActiveExerciseViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PerformTrainingFragment(
    // TODO
) : BaseFragment<FragmentPerformTrainingBinding, PerformTrainingViewModel>(
    FragmentPerformTrainingBinding::inflate
) {
    private val args by navArgs<PerformTrainingFragmentArgs>()

    @Inject
    lateinit var assistedFactory: PerformTrainingViewModelAssistedFactory

    override val viewModel: PerformTrainingViewModel by viewModelCreator {
        assistedFactory.create(
            args.trainingProgram
        )
    }

    private val trainingProgramAdapter by lazy {
        SimpleAdapter(
            ViewHolderActiveExerciseBinding::inflate,
            {
                ActiveExerciseViewHolder(it) { item ->
                    viewModel.updateExerciseStatus(item)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerformTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclers()
        initObservers()
        initListeners()
    }

    private fun initRecyclers() {
        binding.exerciseRecycler.apply {
            adapter = trainingProgramAdapter
        }
    }

    private fun initObservers() {
        viewModel.apply {
            binding.apply {
                trainingProgramVM.observe(viewLifecycleOwner) {
                    trainingProgramAdapter.swapItems(it.exercise)
                }
                timeTraining.observe(viewLifecycleOwner) {
                    timerEditText.setText(it)
                }
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            timerEditText.apply {
                addMaskedChangeListener(TIME_SHORT_MASK)
                setOnFocusChangeListener { view, isFocusState ->
                    viewModel.onTimerFocusChange(isFocusState)
                }
            }
        }
    }
}
