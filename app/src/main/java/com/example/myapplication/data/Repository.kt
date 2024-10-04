package com.example.myapplication.data

import com.example.myapplication.domain.model.Contacto

object Repository {

    private val contactos = mutableListOf<Contacto>()

    init {
        contactos.add(Contacto("Juanito"))
        contactos.add(Contacto("Jorgito"))
        contactos.add(Contacto("Jaimito"))
    }

    private val mapContactos = mutableMapOf<String, Contacto>()

    fun addContacto(contacto: Contacto) = contactos.add(contacto)

    fun getContactos(): List<Contacto> {
        return contactos
    }

    fun deleteContacto(contacto: Contacto): Any {
        return contactos.remove(contacto)
    }
}