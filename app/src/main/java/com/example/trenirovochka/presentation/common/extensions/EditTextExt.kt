package com.example.trenirovochka.presentation.common.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

fun EditText.addMaskedChangeListener(mask: String) {
    this.addTextChangedListener(MaskedTextChangedListener(mask, this))
}

fun EditText.addKeyDoneListener(action: () -> Unit) {
    imeOptions = EditorInfo.IME_ACTION_DONE
    setOnEditorActionListener { _, keyCode, _ ->
        if (keyCode == EditorInfo.IME_ACTION_DONE) {
            action.invoke()
            hideKeyboard()
            true
        } else {
            false
        }
    }
}
