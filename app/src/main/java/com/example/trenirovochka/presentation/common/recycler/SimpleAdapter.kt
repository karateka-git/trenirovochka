package com.example.trenirovochka.presentation.common.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class SimpleAdapter<T : Any, VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val createViewHolder: (VB) -> SimpleViewHolder<T>
) : RecyclerView.Adapter<SimpleViewHolder<T>>() {

    private val differ = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem === newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }
        }
    )

    fun swapItems(newItems: List<T>) {
        differ.submitList(newItems)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder<T> {
        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        return createViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder<T>, position: Int) {
        val item = differ.currentList[position]
        holder.bindTo(item, position)
    }
}
