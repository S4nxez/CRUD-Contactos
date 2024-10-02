package com.example.myapplication.domain.model

data class Contacto(val nombre : String = "Contacto",
                    val email : String = "",
                    val pwd : String = "",
                    val genero : Boolean = false,
                    var bloquear : Boolean = false,
                    var estrellas : Float = 0.0f,
                    var frecuencia : Float = 0.0f,
                    val venderDatos : Boolean = false,)
