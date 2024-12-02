package com.example.myapplication.data.remote.apiServices

import com.example.myapplication.data.remote.modelo.ObjetoRemoto
import com.example.myapplication.data.remote.modelo.ObjetoRemotoItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PersonajeService {
    @GET("users")
    suspend fun getUsers() : Response<ObjetoRemoto>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id : Int) : Response<ObjetoRemotoItem>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<ObjetoRemotoItem>

    @POST("users")
    suspend fun addUser(@Body objetoRemoto: ObjetoRemotoItem): Response<ObjetoRemotoItem>

    @PUT("users/{id}")
    suspend fun editUser(@Path("id") id : Int) : Response<ObjetoRemotoItem>
}