package com.example.myapplication.data

import com.example.myapplication.R
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.data.remote.apiServices.PersonajeService
import com.example.myapplication.data.remote.apiServices.PostService
import com.example.myapplication.data.remote.datasource.PersonajeRemoteDataSource
import com.example.myapplication.data.remote.modelo.toContacto
import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.model.toObjetoRemotoItem
import timber.log.Timber
import javax.inject.Inject


class ObjetosRepository @Inject constructor(
    private val userService: PersonajeService,
    private val postService: PostService,
    private val personajeRemoteDataSource: PersonajeRemoteDataSource,
    ) {
    suspend fun fetchUsers(): NetworkResult<List<Contacto>?> {
        return personajeRemoteDataSource.fetchUsers()
    }

    suspend fun fetchUser(id: Int): NetworkResult<Contacto> {
        try {
            val response = userService.getUser(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toContacto())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Timber.e(e.message, e)
            return error(e.message ?: e.toString())
        }
    }

    suspend fun deleteUser(id: Int): NetworkResult<Contacto> {
        try {
            val response = personajeRemoteDataSource.deleteUser(id)
            if (response is NetworkResult.Success && response.data) {
                Timber.d("deletedUser: $id")
                return NetworkResult.Success(Contacto(id = id))
            }
            return error(R.string.E_DELETE_USER) //TODO diego
        } catch (e: Exception) {
            Timber.e(e.message, e)
            return error(e.message ?: e.toString())
        }
    }

    suspend fun editUser(contacto: Contacto): NetworkResult<Contacto> {
        try {
            val response = userService.editUser(contacto.id)
            if (response.isSuccessful) {
                return NetworkResult.Success(contacto)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Timber.e(e.message, e)
            return error(e.message ?: e.toString())
        } catch (e: Exception) {
            Timber.e(e.message, e)
            return error(e.message ?: e.toString())
        }
    }

    suspend fun addContacto(contacto: Contacto): NetworkResult<Contacto> {
        try {
            val response = userService.addUser(contacto.toObjetoRemotoItem())
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toContacto())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Timber.e(e.message, e)
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

    suspend fun addPost(post: Post): NetworkResult<Post> {
        try {
            val response = postService.addPost(post.toPostItem())
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toPost())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Timber.e(e.message, e)
            return error(e.message ?: e.toString())
        }
    }
}