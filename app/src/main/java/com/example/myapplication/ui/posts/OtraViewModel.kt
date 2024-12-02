package com.example.myapplication.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.useCases.posts.AddPostUseCase
import com.example.myapplication.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtraViewModel @Inject constructor (
    private val addPostUseCase: AddPostUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(OtraState())
    val uiState: LiveData<OtraState> get() = _uiState

    fun handleEvent(event: OtraEvent) {
        when (event) {
            is OtraEvent.AddPost -> addPost(event.post)
            is OtraEvent.ClearEvent -> clearEvent()
        }
    }

    private fun addPost(post: Post) {
        viewModelScope.launch {
            when (val result = addPostUseCase.invoke(post)) {
                is NetworkResult.Error -> {
                    _uiState.value = OtraState().copy(
                        event =
                        UiEvent.ShowSnackbar(result.message, null)
                    )
                }
                is NetworkResult.Loading -> Unit
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        event = UiEvent.PopBackStack,
                    )
                }
            }
        }
    }

    private fun clearEvent() {
        _uiState.value = _uiState.value?.copy(event = null)
    }
}
