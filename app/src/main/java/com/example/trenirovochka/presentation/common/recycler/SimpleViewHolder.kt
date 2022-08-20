package com.example.trenirovochka.presentation.common.recycler

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleViewHolder<T>(containerView: View) :
    RecyclerView.ViewHolder(containerView) {

    private var item: T? = null

    @CallSuper
    open fun bindTo(item: T, pos: Int) {
        this.item = item
    }

    open fun getItem(): T? {
        return item
    }
}
