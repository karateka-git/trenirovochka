package com.example.trenirovochka.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.trenirovochka.domain.extensions.onDestroyNullable

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val inflate: (
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) -> VB
) : Fragment() {

    protected var binding by onDestroyNullable<VB>()
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigationObserver()
        initOnBackPressedCallback()
    }

    protected open fun onBackPressed() {
        viewModel.exit()
    }

    private fun initNavigationObserver() {
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            it?.getNotConsumedContent()?.invoke(findNavController())
        }
    }

    private fun initOnBackPressedCallback() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner) {
                onBackPressed()
            }
    }
}
