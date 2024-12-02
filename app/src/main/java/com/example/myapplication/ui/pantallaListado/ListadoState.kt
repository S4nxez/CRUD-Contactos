package com.example.myapplication.ui.pantallaListado

import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.ui.utils.UiEvent

data class ListadoState(
    val contactos: List<Contacto> = emptyList(),
    val isIrDetalle: Boolean = false,
    val isLoading: Boolean = true,
    val event: UiEvent? = null,
)
