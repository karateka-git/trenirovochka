package com.example.trenirovochka.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.trenirovochka.data.local.ActionWithDate
import com.example.trenirovochka.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.apply {
            binding.apply {
                trainingProgram.observe(viewLifecycleOwner) {
                    text.text = it.name
                }
                selectedDate.observe(viewLifecycleOwner) {
                    datePickerSelectedDateText.text = it
                }
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            datePickerForwardButton.setOnClickListener {
                viewModel.updateSelectedDate(ActionWithDate.NEXT)
            }
            datePickerBackButton.setOnClickListener {
                viewModel.updateSelectedDate(ActionWithDate.PREV)
            }
        }
    }
}
