package com.example.trenirovochka.presentation.screens.editTrainingProgram.viewHolders

import com.example.trenirovochka.databinding.ViewHolderEditExerciseBinding
import com.example.trenirovochka.presentation.common.recycler.SimpleViewHolder
import com.example.trenirovochka.presentation.screens.editTrainingProgram.models.EditExercise

class EditExerciseViewHolder(
    private val binding: ViewHolderEditExerciseBinding,
    private val selectClickListener: (EditExercise) -> Unit
) : SimpleViewHolder<EditExercise>(binding.root) {

    init {
        binding.root.setOnClickListener {
            getItem()?.let {
                selectClickListener(it)
            }
        }
    }

    override fun bindTo(
        item: EditExercise,
        pos: Int
    ) {
        super.bindTo(item, pos)
        binding.apply {
            root.isSelected = item.isSelected
            title.text = item.name
            executionDescription.text = item.getExecutionDescription(root.context)
        }
    }
}
