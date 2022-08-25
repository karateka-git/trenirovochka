package com.example.trenirovochka.presentation.common.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, 0)
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun View.changeTopMargin(marginInPx: Int) {
    val mpl = layoutParams as ViewGroup.MarginLayoutParams
    mpl.topMargin = marginInPx
    layoutParams = mpl
}

fun View.changeBottomMargin(marginInPx: Int) {
    val mpl = layoutParams as ViewGroup.MarginLayoutParams
    mpl.bottomMargin = marginInPx
    layoutParams = mpl
}

fun View.setVisible(visible: Boolean, invisibilityType: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else invisibilityType
}
