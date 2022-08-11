package com.example.trenirovochka.domain.datacontracts.local

interface IResourcesRepository {

    fun getStringFromResources(resId: Int, vararg args: Any): String

    fun getPluralsFromResources(resId: Int, quantity: Int, vararg args: Any): String
}
