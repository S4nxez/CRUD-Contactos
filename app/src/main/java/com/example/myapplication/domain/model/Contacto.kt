package com.example.myapplication.domain.model

import com.example.myapplication.data.remote.modelo.ObjetoRemotoItem

data class Contacto(val id : Int = 0,
                    val nombre : String = "",
                    val job : String = "",
                    val radioEl : Boolean = true,
                    val radioElla : Boolean = false,
                    val actor : String = "",
                    val foto : String = "https://thispersondoesnotexist.com/",
                    )

fun Contacto.toContacto() : Contacto =
    Contacto(id, nombre, job, radioEl, radioElla, actor)

fun Contacto.toObjetoRemotoItem() : ObjetoRemotoItem =
    ObjetoRemotoItem(id = id, name = nombre, website = foto)