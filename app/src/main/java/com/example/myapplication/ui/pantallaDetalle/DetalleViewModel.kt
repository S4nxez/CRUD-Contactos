package com.example.myapplication.ui.pantallaDetalle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.domain.model.toContacto
import com.example.myapplication.domain.useCases.contactos.*
import com.example.myapplication.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleViewModel @Inject constructor(
    private val getContactoById: GetContactoById,
    private val addContactoUseCase: AddContactoUseCase,
    private val deleteContactoUseCase: DeleteContactoByIdUseCase,
    private val updateContactoUseCase: EditContactoUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(DetalleState())
    val uiState: LiveData<DetalleState> get() = _uiState

    fun handleEvent(event: DetalleEvent) {
        when (event) {
            is DetalleEvent.GetContactoByNombre -> getContacto(event.id)
            is DetalleEvent.DeleteContacto      -> deleteContacto(event.id)
            is DetalleEvent.UpdateContacto      -> updateContacto(event.contacto)
            is DetalleEvent.AddContacto         -> addContacto(event.contacto)
            is DetalleEvent.ClearEvent          -> clearEvent()
        }
    }

    private fun updateContacto(contacto: Contacto) {
        viewModelScope.launch {
            when (val result = updateContactoUseCase.invoke(contacto)) {
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(
                        event = UiEvent.ShowSnackbar(result.message),
                        isLoading = false
                    )

                }

                is NetworkResult.Loading -> _uiState.value = _uiState.value?.copy(isLoading = true)
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        event = UiEvent.PopBackStack,
                        isLoading = false
                    )

                }
            }
        }
    }

    private fun deleteContacto(id: Int) {
        viewModelScope.launch {
            when (val result = deleteContactoUseCase.invoke(id)) {
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(
                        event = UiEvent.ShowSnackbar(result.message),
                        isLoading = false
                    )

                }

                is NetworkResult.Loading -> _uiState.value = _uiState.value?.copy(isLoading = true)
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        event = UiEvent.PopBackStack,
                        isLoading = false
                    )

                }
            }
        }

    }

    private fun addContacto(contacto: Contacto) {
        viewModelScope.launch {
            when (val result = addContactoUseCase.invoke(contacto)) {
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(
                        event = UiEvent.ShowSnackbar(result.message),
                        isLoading = false
                    )

                }

                is NetworkResult.Loading -> _uiState.value = _uiState.value?.copy(isLoading = true)
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        event = UiEvent.PopBackStack,
                        isLoading = false
                    )

                }
            }
        }
    }

    private fun getContacto(id: Int) {
        viewModelScope.launch {
            when (val result = getContactoById.invoke(id)) {
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(
                        event = UiEvent.ShowSnackbar(result.message),
                        isLoading = false
                    )

                }

                is NetworkResult.Loading -> _uiState.value = _uiState.value?.copy(isLoading = true)
                is NetworkResult.Success -> {
                    val persona = result.data.toContacto()
                    _uiState.value = _uiState.value?.copy(contacto = persona, isLoading = false)
                }
            }
        }
    }

    private fun clearEvent() {
        _uiState.value = _uiState.value?.copy(event = null)
    }
}
