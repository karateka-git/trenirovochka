package com.example.trenirovochka.domain.interactors.interfaces

interface IResourcesInteractor {

    fun getStringFromResources(resId: Int, vararg args: Any): String

    fun getPluralsFromResources(resId: Int, quantity: Int, vararg args: Any): String
}
