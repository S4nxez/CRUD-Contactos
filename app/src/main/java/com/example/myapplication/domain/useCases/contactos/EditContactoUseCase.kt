package com.example.myapplication.domain.useCases.contactos

import com.example.myapplication.data.ObjetosRepository
import com.example.myapplication.domain.model.Contacto
import javax.inject.Inject

class EditContactoUseCase @Inject constructor(private val objetosRepository: ObjetosRepository) {
    suspend operator fun invoke(contacto: Contacto) = objetosRepository.editUser(contacto)
}
