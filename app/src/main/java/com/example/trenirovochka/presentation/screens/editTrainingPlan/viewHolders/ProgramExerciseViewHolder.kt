package com.example.trenirovochka.presentation.screens.editTrainingPlan.viewHolders

import com.example.trenirovochka.databinding.ViewHolderProgramExerciseBinding
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.presentation.common.recycler.SimpleViewHolder

class ProgramExerciseViewHolder(
    private val binding: ViewHolderProgramExerciseBinding,
) : SimpleViewHolder<Exercise>(binding.root) {

    override fun bindTo(item: Exercise, pos: Int) {
        super.bindTo(item, pos)
        binding.apply {
            exerciseName.text = item.name
        }
    }
}