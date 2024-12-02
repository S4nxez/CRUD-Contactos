package com.example.myapplication.domain.useCases.contactos

import com.example.myapplication.R
import com.example.myapplication.data.ObjetosRepository
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.ui.utils.StringProvider
import javax.inject.Inject

class AddContactoUseCase @Inject constructor(
    private val objetosRepository: ObjetosRepository,
    private val stringProvider: StringProvider,
) {
    suspend operator fun invoke(contacto: Contacto): NetworkResult<Contacto> {
        if (contacto.nombre.isBlank())
            return NetworkResult.Error(stringProvider.getString(R.string.E_NOMBRE_VACIO))
        return objetosRepository.addContacto(contacto)
    }
}
