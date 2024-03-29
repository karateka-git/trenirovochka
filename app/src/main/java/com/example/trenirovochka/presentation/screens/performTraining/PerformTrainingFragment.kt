package com.example.trenirovochka.presentation.screens.performTraining

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.navigation.fragment.navArgs
import com.example.trenirovochka.databinding.FragmentPerformTrainingBinding
import com.example.trenirovochka.databinding.ViewHolderActiveExerciseBinding
import com.example.trenirovochka.domain.extensions.formatAsTime
import com.example.trenirovochka.domain.models.RecoveryLevel
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.domain.models.ExecutionStatus.IN_PROGRESS
import com.example.trenirovochka.domain.models.UserStatus
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.extensions.addKeyDoneListener
import com.example.trenirovochka.presentation.common.extensions.addMaskedChangeListener
import com.example.trenirovochka.presentation.common.extensions.getThemeColor
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
import com.example.trenirovochka.presentation.common.recycler.SimpleAdapter
import com.example.trenirovochka.presentation.common.util.TextMask.TIME_SHORT_MASK
import com.example.trenirovochka.presentation.screens.main.SharedCurrentTrainingViewModel
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
    override val viewModel: PerformTrainingViewModel by viewModelCreator()

    private val sharedCurrentTrainingViewModel: SharedCurrentTrainingViewModel by viewModelCreator(
        ::requireActivity
    )

    private val trainingProgramAdapter by lazy {
        SimpleAdapter(
            ViewHolderActiveExerciseBinding::inflate
        ) {
            ActiveExerciseViewHolder(it) { item ->
                sharedCurrentTrainingViewModel.updateExercises(item)
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
        binding.apply {
            viewModel.apply {
                userState.observe(viewLifecycleOwner) {
                    updateUserState(it)
                }
            }
            sharedCurrentTrainingViewModel.apply {
                timeTraining.observe(viewLifecycleOwner) {
                    timerEditText.setText(formatAsTime(it))
                    trainingProgram.value?.let { currentProgram ->
                        viewModel.updateUserState(
                            it,
                            sharedCurrentTrainingViewModel.getTimeForRelax(),
                            currentProgram.status
                        )
                    }
                }
                trainingProgram.observe(viewLifecycleOwner) {
                    it?.let {
                        updateProgramState(it)
                        trainingProgramAdapter.swapItems(it.exercises)
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            timerEditText.apply {
                addMaskedChangeListener(TIME_SHORT_MASK)
                setOnFocusChangeListener { _, isFocusState ->
                    sharedCurrentTrainingViewModel.onChangeTimerPauseState(isFocusState)
                }
                addKeyDoneListener {
                    sharedCurrentTrainingViewModel.changeTimeForRelax(text.toString())
                }
            }
            toolbar.setBackButtonOnClickListener { onBackPressed() }
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
        @AttrRes backgroundColor: Int = colorSurfaceVariant
    ) {
        binding.timerContainer.setBackgroundColor(
            requireContext().getThemeColor(backgroundColor)
        )
    }
}
