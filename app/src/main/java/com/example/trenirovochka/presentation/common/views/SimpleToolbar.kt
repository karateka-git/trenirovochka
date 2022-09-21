package com.example.trenirovochka.presentation.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getStringOrThrow
import com.example.trenirovochka.R
import com.example.trenirovochka.databinding.SimpleToolbarViewBinding
import com.example.trenirovochka.presentation.common.extensions.setVisible

class SimpleToolbar(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(
    context,
    attrs
) {

    private val binding by lazy {
        SimpleToolbarViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        attrs.apply {
            val typedArray = context.obtainStyledAttributes(
                this,
                R.styleable.SimpleToolbar,
                0,
                0
            )
            if (typedArray.hasValue(R.styleable.SimpleToolbar_st_title)) {
                val titleResId = typedArray.getStringOrThrow(
                    R.styleable.SimpleToolbar_st_title
                )
                setTitle(titleResId)
            }
            if (typedArray.hasValue(R.styleable.SimpleToolbar_st_is_visible_back_button)) {
                val isVisibleBackButton = typedArray.getBoolean(
                    R.styleable.SimpleToolbar_st_is_visible_back_button,
                    true
                )
                setVisibleBackButton(isVisibleBackButton)
            }
            typedArray.recycle()
        }
    }

    fun setBackButtonOnClickListener(listener: OnClickListener) {
        binding.backButton.setOnClickListener(listener)
    }

    private fun setTitle(title: String) {
        binding.title.text = title
    }

    private fun setVisibleBackButton(isVisible: Boolean) {
        binding.backButton.setVisible(isVisible)
    }
}
