package com.example.myapplication.ui.pantallaCrear

import com.example.myapplication.domain.model.Contacto

interface CrearEvent {
    data object ClearEvent : CrearEvent
    class AddContacto(val contacto: Contacto) : CrearEvent
}