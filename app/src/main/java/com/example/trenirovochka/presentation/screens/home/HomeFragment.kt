package com.example.trenirovochka.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.trenirovochka.R
import com.example.trenirovochka.data.local.models.ActionWithDate
import com.example.trenirovochka.databinding.FragmentHomeBinding
import com.example.trenirovochka.databinding.ViewHolderExerciseBinding
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.recycler.SimpleAdapter
import com.example.trenirovochka.presentation.common.recycler.decorations.VerticalDividerDecoration
import com.example.trenirovochka.presentation.screens.home.viewHolders.ExerciseViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()

    private val trainingProgramAdapter by lazy {
        SimpleAdapter(
            ViewHolderExerciseBinding::inflate,
            { ExerciseViewHolder(it) }
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
                        R.drawable.vertical_divider_dark
                    )
                )
            )
        }
    }

    private fun initObservers() {
        viewModel.apply {
            binding.apply {
                trainingProgram.observe(viewLifecycleOwner) {
                    trainingProgramAdapter.swapItems(it.exercise)
                }
                selectedDate.observe(viewLifecycleOwner) {
                    datePickerSelectedDateText.text = it
                }
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            datePickerForwardButton.setOnClickListener {
                viewModel.updateSelectedDate(ActionWithDate.NEXT)
            }
            datePickerBackButton.setOnClickListener {
                viewModel.updateSelectedDate(ActionWithDate.PREV)
            }
            startTrainingButton.setOnClickListener {
                viewModel.onStartTrainingButtonClick()
            }
        }
    }
}
