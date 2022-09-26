package com.example.trenirovochka.presentation.screens.editTrainingPlan.viewHolders

import com.example.trenirovochka.databinding.ViewHolderTrainingProgramBinding
import com.example.trenirovochka.databinding.ViewHolderTrainingProgramExerciseBinding
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.presentation.common.recycler.SimpleAdapter
import com.example.trenirovochka.presentation.common.recycler.SimpleViewHolder

class TrainingProgramViewHolder(
    private val binding: ViewHolderTrainingProgramBinding,
    private val actionClickListener: (TrainingProgram) -> Unit
) : SimpleViewHolder<TrainingProgram>(binding.root) {

    private val exerciseAdapter by lazy {
        SimpleAdapter(
            ViewHolderTrainingProgramExerciseBinding::inflate
        ) {
            TrainingProgramExerciseViewHolder(it)
        }
    }

    init {
        binding.root.setOnClickListener {
            getItem()?.let {
                actionClickListener(it)
            }
        }
        binding.trainingProgramExercisesList.adapter = exerciseAdapter
    }

    override fun bindTo(item: TrainingProgram, pos: Int) {
        super.bindTo(item, pos)
        exerciseAdapter.swapItems(item.exercise)
        binding.apply {
            trainingProgramName.text = item.name
        }
    }
}