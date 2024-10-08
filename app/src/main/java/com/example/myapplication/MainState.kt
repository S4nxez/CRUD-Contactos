package com.example.myapplication

import com.example.myapplication.domain.model.Contacto

data class MainState(
    val contacto: Contacto? = null,
    val error: String? = null,
    val indiceMain: Int = 0,
    val tamanyo: Int = 0,
    val isUltimo: Boolean = false,
    val isPrimero: Boolean = true,
)
