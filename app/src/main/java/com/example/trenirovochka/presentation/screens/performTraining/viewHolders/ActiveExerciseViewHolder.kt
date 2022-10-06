package com.example.trenirovochka.presentation.screens.performTraining.viewHolders

import com.example.trenirovochka.databinding.ViewHolderActiveExerciseBinding
import com.example.trenirovochka.domain.models.Exercise
import com.example.trenirovochka.domain.models.ExecutionStatus.NOT_STARTED
import com.example.trenirovochka.presentation.common.extensions.setVisible
import com.example.trenirovochka.presentation.common.recycler.SimpleViewHolder

class ActiveExerciseViewHolder(
    private val binding: ViewHolderActiveExerciseBinding,
    private val actionClickListener: (Exercise) -> Unit
) : SimpleViewHolder<Exercise>(binding.root) {

    init {
        binding.exerciseActionButton.setOnClickListener {
            getItem()?.let {
                actionClickListener(it)
            }
        }
    }

    override fun bindTo(item: Exercise, pos: Int) {
        super.bindTo(item, pos)
        binding.apply {
            exerciseName.text = item.name
            exerciseDescription.text = item.getActiveExecutionDescription(root.context)
            exerciseActionButton.isChecked = item.isStatusInProgress()
            progressIndicator.apply {
                setVisible(item.status != NOT_STARTED)
                max = item.numberOfTotalSets
                progress = item.numberOfCompletedSets
            }
        }
    }
}
