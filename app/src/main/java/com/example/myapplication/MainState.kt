package com.example.myapplication

import com.example.myapplication.domain.model.Contacto

data class MainState(
    val contacto: Contacto? = null,
    val error: String? = null,
)
