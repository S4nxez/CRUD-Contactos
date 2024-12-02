package com.example.myapplication.data.remote.modelo.posts

import com.example.myapplication.domain.model.Post

data class CommentItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
) {
    fun toPost(): Post {
        return Post(
            id = id,
            nombre = name,
            email = email
        )
    }
}