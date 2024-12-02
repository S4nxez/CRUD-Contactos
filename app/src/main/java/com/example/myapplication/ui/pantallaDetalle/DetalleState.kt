package com.example.myapplication.ui.pantallaDetalle

import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.ui.utils.UiEvent

data class DetalleState(
    val contacto: Contacto? = null,
    val isLoading: Boolean = true,
    val event: UiEvent? = null,
)
