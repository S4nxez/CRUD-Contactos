package com.example.myapplication.data.remote.apiServices

import com.example.myapplication.data.remote.modelo.posts.CommentItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostService {
    @POST("comments")
    suspend fun addPost(@Body post: CommentItem): Response<CommentItem>
}
