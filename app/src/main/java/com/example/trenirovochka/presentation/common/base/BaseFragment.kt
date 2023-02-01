package com.example.trenirovochka.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.trenirovochka.domain.extensions.onDestroyNullable
import com.example.trenirovochka.presentation.common.extensions.changeBottomMargin
import com.example.trenirovochka.presentation.common.extensions.changeTopMargin

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
        applyWindowsInsetsListener()
    }

    protected open fun fitSystemWindowInsets(view: View, windowInsets: WindowInsetsCompat): WindowInsetsCompat {
        val insets =
            windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
        view.changeTopMargin(insets.top)
        view.changeBottomMargin(insets.bottom)
        return WindowInsetsCompat.Builder().setInsets(
            WindowInsetsCompat.Type.systemBars(),
            Insets.of(
                insets.left,
                0,
                insets.right,
                0
            )
        ).build()
    }

    open fun keyboardIsVisibleChanged(isVisible: Boolean) {}

    protected open fun onBackPressed() {
        viewModel.exit()
    }

    private fun initNavigationObserver() {
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            it?.getNotConsumedContent()?.invoke(findNavController())
        }
    }

    private fun applyWindowsInsetsListener() {
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.root
        ) { v, windowInsets ->
            val keyboardIsVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime())
            keyboardIsVisibleChanged(keyboardIsVisible)

            fitSystemWindowInsets(v, windowInsets)
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
