package com.example.myapplication.ui.pantallaCrear

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.domain.useCases.contactos.AddContactoUseCase
import com.example.myapplication.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrearViewModel @Inject constructor (
    private val addContactoUseCase: AddContactoUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(CrearState())
    val uiState: LiveData<CrearState> get() = _uiState

    fun handleEvent(event: CrearEvent) {
        when (event) {
            is CrearEvent.AddContacto   -> addContacto(event.contacto)
            is CrearEvent.ClearEvent    -> clearEvent()
        }
    }

    private fun addContacto(contacto: Contacto) {
        viewModelScope.launch {
            when (val result = addContactoUseCase.invoke(contacto)) {
                is NetworkResult.Error -> {
                    _uiState.value = CrearState().copy(
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
