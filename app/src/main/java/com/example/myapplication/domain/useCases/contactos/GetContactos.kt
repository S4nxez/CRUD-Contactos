package com.example.myapplication.domain.useCases.contactos

import com.example.myapplication.data.Repository

class GetContactos( private val repo : Repository){
    operator fun invoke() = repo.getContactos()
}