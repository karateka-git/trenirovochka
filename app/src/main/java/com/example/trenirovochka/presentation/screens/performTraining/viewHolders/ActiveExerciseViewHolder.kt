package com.example.trenirovochka.presentation.screens.performTraining.viewHolders

import com.example.trenirovochka.databinding.ViewHolderActiveExerciseBinding
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.presentation.common.recycler.SimpleViewHolder

class ActiveExerciseViewHolder(
    private val binding: ViewHolderActiveExerciseBinding,
    private val actionClickListener: (Exercise) -> Unit
) : SimpleViewHolder<Exercise>(binding.root) {

    override fun bindTo(item: Exercise, pos: Int, onClickCallback: ((Exercise, Int) -> Unit)?) {
        binding.apply {
            exerciseName.text = item.name
            exerciseDescription.text = item.getActiveExecutionDescription(root.context)
            exerciseActionButton.apply {
                isChecked = item.status
                setOnClickListener {
                    actionClickListener(item)
                }
            }
        }
    }
}
