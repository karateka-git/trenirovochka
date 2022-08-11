package com.example.trenirovochka.domain.interactors

import com.example.trenirovochka.domain.datacontracts.local.IResourcesRepository
import com.example.trenirovochka.domain.interactors.interfaces.IResourcesInteractor
import javax.inject.Inject

class ResourcesInteractor @Inject constructor(
    private val resourcesRepository: IResourcesRepository
) : IResourcesInteractor {

    override fun getStringFromResources(resId: Int, vararg args: Any) = resourcesRepository.getStringFromResources(resId, *args)

    override fun getPluralsFromResources(resId: Int, quantity: Int, vararg args: Any) =
        resourcesRepository.getPluralsFromResources(resId, quantity, *args)
}
