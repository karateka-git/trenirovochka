package com.example.trenirovochka.presentation.common.recycler.decorations

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class VerticalDividerDecoration(private val drawableDivider: Drawable?) : RecyclerView.ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        if (drawableDivider == null) return

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)

            val dividerStart: Int = child.paddingStart
            val dividerTop: Int = child.bottom
            val dividerEnd: Int = child.width - child.paddingEnd
            val dividerBottom: Int = dividerTop + drawableDivider.intrinsicHeight

            drawableDivider.apply {
                bounds = Rect(dividerStart, dividerTop, dividerEnd, dividerBottom)
                draw(canvas)
            }
        }
    }
}
