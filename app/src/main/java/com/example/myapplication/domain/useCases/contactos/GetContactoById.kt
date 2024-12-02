package com.example.myapplication.domain.useCases.contactos

import com.example.myapplication.data.ObjetosRepository
import javax.inject.Inject

class GetContactoById @Inject constructor(private val objetosRepository: ObjetosRepository){
    suspend operator fun invoke(id:Int) = objetosRepository.fetchUser(id)
}