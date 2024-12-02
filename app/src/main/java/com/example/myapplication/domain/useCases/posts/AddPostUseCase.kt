package com.example.myapplication.domain.useCases.posts

import com.example.myapplication.R
import com.example.myapplication.data.ObjetosRepository
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.domain.model.Post
import com.example.myapplication.ui.utils.StringProvider
import javax.inject.Inject

class AddPostUseCase @Inject constructor(
    private val stringProvider: StringProvider,
    private val objetosRepository: ObjetosRepository
) {
    suspend operator fun invoke(post: Post): NetworkResult<Post> {
        if (post.nombre.isBlank())
            return NetworkResult.Error(stringProvider.getString(R.string.E_NOMBRE_VACIO))
        return objetosRepository.addPost(post)
    }
}
