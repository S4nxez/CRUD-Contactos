package com.example.myapplication.domain.useCases.contactos

import com.example.myapplication.data.Repository

class GetContactos(){
    operator fun invoke() = Repository.getContactos()
}