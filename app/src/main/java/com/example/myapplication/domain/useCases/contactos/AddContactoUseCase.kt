package com.example.myapplication.domain.useCases.contactos

import com.example.myapplication.data.Repository
import com.example.myapplication.domain.model.Contacto

class AddContactoUseCase {
    operator fun invoke(contacto: Contacto): Boolean {
        return Repository.addContacto(contacto)
    }
}
