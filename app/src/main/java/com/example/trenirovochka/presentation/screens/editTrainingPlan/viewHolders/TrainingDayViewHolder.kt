package com.example.trenirovochka.presentation.screens.editTrainingPlan.viewHolders

import com.example.trenirovochka.databinding.ViewHolderTrainingDayBinding
import com.example.trenirovochka.domain.models.TrainingDay
import com.example.trenirovochka.presentation.common.recycler.SimpleViewHolder

class TrainingDayViewHolder(
    private val binding: ViewHolderTrainingDayBinding,
    private val actionClickListener: (TrainingDay) -> Unit
) : SimpleViewHolder<TrainingDay>(binding.root) {
    init {
        binding.trainingDayItem.setOnClickListener {
            getItem()?.let {
                actionClickListener(it)
            }
        }
    }

    override fun bindTo(item: TrainingDay, pos: Int) {
        super.bindTo(item, pos)
        binding.apply {
            trainingDayItem.isChecked = item.isSelected
            trainingDayItem.text = item.dayOfWeek.getShortDisplayName()
        }
    }
}