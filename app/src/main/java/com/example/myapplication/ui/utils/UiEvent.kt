package com.example.myapplication.ui.utils

sealed class UiEvent {

    data object PopBackStack: UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent()

}
