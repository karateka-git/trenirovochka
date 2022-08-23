package com.example.trenirovochka.presentation.common.extensions

import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

fun EditText.addMaskedChangeListener(mask: String) {
    this.addTextChangedListener(MaskedTextChangedListener(mask, this))
}
