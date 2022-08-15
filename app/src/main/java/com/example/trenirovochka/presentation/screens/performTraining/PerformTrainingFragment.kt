package com.example.trenirovochka.presentation.screens.performTraining

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.trenirovochka.databinding.FragmentPerformTrainingBinding
import com.example.trenirovochka.presentation.common.base.BaseFragment
import com.example.trenirovochka.presentation.common.extensions.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PerformTrainingFragment(
    // TODO
) : BaseFragment<FragmentPerformTrainingBinding, PerformTrainingViewModel>(
    FragmentPerformTrainingBinding::inflate
) {

    companion object {
        const val TRAINING_PROGRAM_TAG = "TRAINING_PROGRAM_TAG"
    }

    @Inject
    lateinit var assistedFactory: PerformTrainingViewModelAssistedFactory

    override val viewModel: PerformTrainingViewModel by viewModelCreator {
        assistedFactory.create(
            arguments?.getParcelable(
                TRAINING_PROGRAM_TAG
            )!!
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerformTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.test.observe(viewLifecycleOwner) {
            binding.button.text = it
        }
    }

    private fun initListeners() {
        binding.button.setOnClickListener {
            viewModel.test.value = "hello"
//            viewModel.backTo(HomeDestination.HomeScreen)
        }
    }
}
