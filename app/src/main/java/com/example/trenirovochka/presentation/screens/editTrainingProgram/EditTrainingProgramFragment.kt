package com.example.trenirovochka.presentation.screens.editTrainingProgram

import android.os.Bundle
import android.view.View
import com.example.trenirovochka.databinding.FragmentEditTrainingProgramBinding
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTrainingProgramFragment : BaseFragment<FragmentEditTrainingProgramBinding, EditTrainingProgramViewModel>(
    FragmentEditTrainingProgramBinding::inflate
) {

    override val viewModel: EditTrainingProgramViewModel by viewModelCreator()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.apply {
            toolbar.setBackButtonOnClickListener {
                viewModel.exit()
            }
        }
    }
}