package com.example.trenirovochka.data.local.repositories

import android.content.Context
import com.example.trenirovochka.domain.datacontracts.local.IResourcesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidResourcesRepository @Inject constructor(
    @ApplicationContext private val appContext: Context
) : IResourcesRepository {

    override fun getStringFromResources(resId: Int, vararg args: Any) = appContext.getString(resId, *args)

    override fun getPluralsFromResources(resId: Int, quantity: Int, vararg args: Any): String =
        appContext.resources.getQuantityString(
            resId,
            quantity,
            *args
        )
}
