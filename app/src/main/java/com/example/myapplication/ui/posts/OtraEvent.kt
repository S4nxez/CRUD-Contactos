package com.example.myapplication.ui.posts

import com.example.myapplication.domain.model.Post

interface OtraEvent {
    class AddPost(val post: Post) : OtraEvent
    data object ClearEvent : OtraEvent
}