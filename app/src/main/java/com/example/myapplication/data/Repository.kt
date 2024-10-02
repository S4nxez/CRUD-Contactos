package com.example.myapplication.data

import com.example.myapplication.domain.model.Contacto

class Repository {
    fun addContacto(contacto: Contacto) = contactos.add(contacto)


    fun getContactos(): List<Contacto> {
        return contactos
    }

    init {
        contactos.add(Contacto("Juanito"))
        contactos.add(Contacto("Jorgito"))
        contactos.add(Contacto("Jaimito"))
    }

    companion object {
        private val contactos = mutableListOf<Contacto>()
        fun getInstance(): Repository = Repository()
    }
}