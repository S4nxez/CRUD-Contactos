package com.example.myapplication.domain.useCases.contactos

import com.example.myapplication.data.Repository
import com.example.myapplication.domain.model.Contacto

class DeleteContactoUseCase {
    operator fun invoke(contacto: Contacto) = Repository.deleteContacto(contacto)
}