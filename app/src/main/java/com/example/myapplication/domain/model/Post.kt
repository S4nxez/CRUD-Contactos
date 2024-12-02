package com.example.myapplication.domain.model

import com.example.myapplication.data.remote.modelo.posts.CommentItem

data class Post(val id : Int = 0,
                    val nombre : String = "",
                    val email : String = "",
    ) {
    fun toPostItem(): CommentItem {
        return CommentItem(
            body = email,
            id = id,
            name = nombre,
            email = email,
            postId = id,
        )
    }
}