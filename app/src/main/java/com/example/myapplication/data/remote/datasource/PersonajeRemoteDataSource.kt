package com.example.myapplication.data.remote.datasource

import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.data.remote.apiServices.PersonajeService
import com.example.myapplication.data.remote.modelo.toContacto
import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.ui.utils.StringProvider
import javax.inject.Inject

class PersonajeRemoteDataSource @Inject constructor(
    private val userService: PersonajeService,
    stringProvider: StringProvider
) : BaseApiResponse(stringProvider) {
    suspend fun fetchUsers(): NetworkResult<List<Contacto>?> =
        safeApiCall { userService.getUsers() }.map { resultado ->
            resultado?.map { result -> result.toContacto() }
        }

    suspend fun deleteUser(id: Int): NetworkResult<Boolean> =
        safeApiCallNoBody { userService.deleteUser(id) }


    suspend fun addUser(): NetworkResult<List<Contacto>?> =
        safeApiCall { userService.getUsers() }.map { resultado ->
            resultado?.map { result -> result.toContacto() }
        }

    suspend fun editUser(): NetworkResult<List<Contacto>?> =
        safeApiCall { userService.getUsers() }.map { resultado ->
            resultado?.map { result -> result.toContacto() }
        }

    suspend fun addPost(): NetworkResult<List<Contacto>?> =
        safeApiCall { userService.getUsers() }.map { resultado ->
            resultado?.map { result -> result.toContacto() }
        }
}