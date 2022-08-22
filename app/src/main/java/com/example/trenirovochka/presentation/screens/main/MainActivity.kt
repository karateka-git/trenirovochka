package com.example.trenirovochka.presentation.screens.main

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.requestApplyInsets
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.trenirovochka.databinding.ActivityMainBinding
import com.example.trenirovochka.presentation.common.extensions.changeBottomMargin
import com.example.trenirovochka.presentation.common.extensions.changeTopMargin
import com.example.trenirovochka.presentation.common.extensions.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        WindowCompat.setDecorFitsSystemWindows(window, false) // require for setOnApplyWindowInsetsListener call
        applyWindowsInsetsListener()
        setContentView(binding.root)
        requestApplyInsets(binding.root)
        navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
    }

    fun applyWindowsInsetsListener() {
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.root
        ) { v, windowInsets ->
            fitsSystemWindows(v, windowInsets)
            fitKeyboardSystemWindows(v, windowInsets)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun fitsSystemWindows(view: View, windowInsets: WindowInsetsCompat) {
        val insets =
            windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
        view.changeTopMargin(insets.top)
        view.changeBottomMargin(insets.bottom)
    }

    private fun fitKeyboardSystemWindows(view: View, windowInsets: WindowInsetsCompat) {
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
        val keyboardIsVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime())

        if (keyboardIsVisible.not()) {
            currentFocus?.clearFocus()
        } else {
            view.changeBottomMargin(insets.bottom)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val currentFocusView: View? = currentFocus
            if (currentFocusView is EditText) {
                val outRect = Rect()
                currentFocusView.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    binding.root.hideKeyboard()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
