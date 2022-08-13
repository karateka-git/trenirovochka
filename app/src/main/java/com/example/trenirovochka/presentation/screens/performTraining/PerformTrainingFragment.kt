package com.example.trenirovochka.presentation.screens.performTraining

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.trenirovochka.databinding.FragmentPerformTrainingBinding
import com.example.trenirovochka.presentation.common.base.BaseFragment

class PerformTrainingFragment(
    // TODO
) : BaseFragment<FragmentPerformTrainingBinding, PerformTrainingViewModel>(
    FragmentPerformTrainingBinding::inflate
) {

    override val viewModel: PerformTrainingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerformTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }
}
