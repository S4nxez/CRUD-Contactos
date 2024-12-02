package com.example.myapplication.ui.pantallaDetalle

import com.example.myapplication.domain.model.Contacto

sealed interface DetalleEvent {
    data object ClearEvent : DetalleEvent
    class       GetContactoByNombre(val id : Int) : DetalleEvent
    class       AddContacto(val contacto: Contacto) : DetalleEvent
    class       UpdateContacto(val contacto: Contacto) : DetalleEvent
    class       DeleteContacto(val id: Int) : DetalleEvent
}