package com.example.trenirovochka.presentation.common.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified VM : ViewModel> Fragment.viewModelCreator(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline f: (() -> VM)? = null
): Lazy<VM> =
    viewModels(
        ownerProducer = ownerProducer,
        factoryProducer = f?.let {
            {
                object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(modelClass: Class<T>): T = f() as T
                }
            }
        }
    )
