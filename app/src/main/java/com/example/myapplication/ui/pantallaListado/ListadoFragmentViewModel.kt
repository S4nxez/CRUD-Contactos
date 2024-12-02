package com.example.myapplication.ui.pantallaListado

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.domain.model.toContacto
import com.example.myapplication.domain.useCases.contactos.GetContactos
import com.example.myapplication.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoFragmentViewModel @Inject constructor(
    private val getContactos: GetContactos,
    ) : ViewModel() {

    private val _uiState = MutableLiveData(ListadoState())
    val uiState: LiveData<ListadoState> get() = _uiState

    fun handleEvents(event : ListadoEvent){
        when (event) {
            is ListadoEvent.GetContactos -> getContactos()
            is ListadoEvent.ClearEvent -> clearEvent()
        }
    }

    private fun getContactos() {
        viewModelScope.launch {
            when (val result = getContactos.invoke()) {
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(
                        event = UiEvent.ShowSnackbar(result.message),
                        isLoading = false
                    )
                }
                is NetworkResult.Loading -> _uiState.value = _uiState.value?.copy(isLoading = true)
                is NetworkResult.Success -> {
                    val personas = result.data?.map{ it.toContacto() }?.toList() ?: emptyList()
                    _uiState.value = _uiState.value?.copy(contactos = personas, isLoading = false)
                }
            }
        }
    }

    private fun clearEvent() {
        _uiState.value = _uiState.value?.copy(event = null)
    }
}
