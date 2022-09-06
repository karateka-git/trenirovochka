package com.example.trenirovochka.presentation.screens.performTraining

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.navigation.fragment.navArgs
import com.example.trenirovochka.databinding.FragmentPerformTrainingBinding
import com.example.trenirovochka.databinding.ViewHolderActiveExerciseBinding
import com.example.trenirovochka.domain.models.RecoveryLevel
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.domain.models.TrainingProgram.Companion.ExecutionStatus.IN_PROGRESS
import com.example.trenirovochka.domain.models.UserStatus
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.extensions.addKeyDoneListener
import com.example.trenirovochka.presentation.common.extensions.addMaskedChangeListener
import com.example.trenirovochka.presentation.common.extensions.getThemeColor
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
import com.example.trenirovochka.presentation.common.recycler.SimpleAdapter
import com.example.trenirovochka.presentation.common.util.TextMask.TIME_SHORT_MASK
import com.example.trenirovochka.presentation.screens.performTraining.viewHolders.ActiveExerciseViewHolder
import com.google.android.material.R.attr.*
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
            ViewHolderActiveExerciseBinding::inflate
        ) {
            ActiveExerciseViewHolder(it) { item ->
                viewModel.updateExercises(item)
            }
        }
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
                    updateProgramState(it)
                }
                timeTraining.observe(viewLifecycleOwner) {
                    timerEditText.setText(it)
                }
                userState.observe(viewLifecycleOwner) {
                    updateUserState(it)
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
                addKeyDoneListener {
                    viewModel.changeTimeForRelax(text.toString())
                }
            }
            backButton.setOnClickListener { onBackPressed() }
        }
    }

    private fun updateProgramState(program: TrainingProgram) {
        binding.timerEditText.isEnabled = program.status != IN_PROGRESS
    }

    private fun updateUserState(userStatus: UserStatus) {
        when (userStatus) {
            is UserStatus.NotStarted -> {
                changeBackgroundTimerContainer()
            }
            is UserStatus.InProgress -> {
                changeBackgroundTimerContainer()
            }
            is UserStatus.InPause -> {
                changeBackgroundTimerContainer(
                    when (userStatus.recoveryLevel) {
                        RecoveryLevel.LOW -> colorSecondaryVariant
                        RecoveryLevel.MEDIUM -> colorSecondary
                        else -> colorSurfaceVariant
                    }
                )
            }
            is UserStatus.Completed -> {
                changeBackgroundTimerContainer()
            }
        }
    }

    private fun changeBackgroundTimerContainer(
        @ColorInt backgroundColor: Int = requireContext().getThemeColor(
            colorSurfaceVariant
        )
    ) {
        binding.timerContainer.setBackgroundColor(backgroundColor)
    }
}
