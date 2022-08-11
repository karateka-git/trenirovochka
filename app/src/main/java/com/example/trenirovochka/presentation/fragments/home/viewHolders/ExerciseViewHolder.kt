package com.example.trenirovochka.presentation.fragments.home.viewHolders

import com.example.trenirovochka.databinding.ViewHolderExerciseBinding
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.presentation.common.recycler.SimpleViewHolder

class ExerciseViewHolder(
    private val binding: ViewHolderExerciseBinding,
) : SimpleViewHolder<Exercise>(binding.root) {

    override fun bindTo(
        item: Exercise,
        pos: Int,
        onClickCallback: ((Exercise, Int) -> Unit)?
    ) {
        binding.apply {
            title.text = item.name
            executionDescription.text = item.getExecutionDescription(root.context)
        }
    }
}
