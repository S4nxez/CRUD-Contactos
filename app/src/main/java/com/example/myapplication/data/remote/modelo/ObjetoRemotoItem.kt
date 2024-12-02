package com.example.myapplication.data.remote.modelo

import com.example.myapplication.domain.model.Contacto

data class ObjetoRemotoItem(
    val address: Address? = null,
    val company: Company? = null,
    val email: String? = null,
    val id: Int,
    val name: String,
    val phone: String? = null,
    val username: String? = null,
    val website: String,
)

fun ObjetoRemotoItem.toContacto(): Contacto { return Contacto( id = id, nombre = name ) }