package com.example.trenirovochka.presentation.common.dialogs

import com.example.trenirovochka.databinding.DialogSimpleBinding
import com.example.trenirovochka.presentation.common.base.BaseDialogActionListener
import com.example.trenirovochka.presentation.common.base.BaseDialogFragment

class SimpleDialog(
    private val title: String,
    override val listener: SimpleDialogListener,
) : BaseDialogFragment<SimpleDialogListener, DialogSimpleBinding>(
    DialogSimpleBinding::inflate
) {

    companion object {
        const val SIMPLE_DIALOG_TAG = "SIMPLE_DIALOG_TAG"
    }

    override fun initViews() {
        binding.apply {
            title.text = this@SimpleDialog.title
            positiveButton.setOnClickListener {
                listener.onPositiveButtonClick()
                this@SimpleDialog.dismiss()
            }
            negativeButton.setOnClickListener {
                listener.onNegativeButtonClick()
                this@SimpleDialog.dismiss()
            }
        }
    }
}

interface SimpleDialogListener : BaseDialogActionListener {
    fun onPositiveButtonClick()
    fun onNegativeButtonClick()
}
