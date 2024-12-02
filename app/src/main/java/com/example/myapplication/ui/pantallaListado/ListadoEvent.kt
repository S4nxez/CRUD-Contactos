package com.example.myapplication.ui.pantallaListado

sealed interface ListadoEvent {
    data object ClearEvent : ListadoEvent
    data object GetContactos : ListadoEvent
}