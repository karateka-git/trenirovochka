package com.example.trenirovochka.presentation.common.util

import android.content.Context
import android.util.AttributeSet
import android.widget.ViewAnimator
import com.example.trenirovochka.R

open class BetterViewAnimator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewAnimator(context, attrs) {

    init {
        attrs.apply {
            val typedArray = context.obtainStyledAttributes(
                this,
                R.styleable.BetterViewAnimator,
                0,
                0
            )
            if (typedArray.hasValue(R.styleable.BetterViewAnimator_bva_visible_child)) {
                visibleId = typedArray.getResourceId(
                    R.styleable.BetterViewAnimator_bva_visible_child,
                    0
                )
            }
            typedArray.recycle()
        }
    }

    private var visibleId: Int = 0

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (visibleId > 0) {
            visibleChildId = visibleId
        }
    }

    var visibleChildId: Int
        get() = getChildAt(displayedChild).id
        set(id) {
            if (visibleChildId == id) return
            for (i in 0 until childCount) {
                if (getChildAt(i).id == id) {
                    displayedChild = i
                    return
                }
            }
            throw IllegalArgumentException("No view with ID $id")
        }
}
