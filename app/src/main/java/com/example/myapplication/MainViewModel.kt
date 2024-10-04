package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.domain.useCases.contactos.AddContactoUseCase
import com.example.myapplication.domain.useCases.contactos.DeleteContactoUseCase
import com.example.myapplication.domain.useCases.contactos.GetContactos
import com.example.myapplication.utils.StringProvider

class MainViewModel(
    private val stringProvider: StringProvider,
    private val addContactoUseCase: AddContactoUseCase,
    private val deleteContactoUseCase: DeleteContactoUseCase,
    private val getContactosUseCase: GetContactos,
): ViewModel() {
    private var indice = 0
    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        val contactos = getContactosUseCase()

        if (contactos.isNotEmpty())
            _uiState.value = MainState(contacto = contactos[indice])
        else
            _uiState.value = MainState(error = "No contacts available") //TODO STRIN
    }

    fun addContacto(contacto: Contacto) {
        if (!addContactoUseCase(contacto)) {
            _uiState.value = MainState(
                    contacto = _uiState.let { contacto },
                    error = stringProvider.getString(R.string.E_CONTACTO_YA_EXISTE)
                )
        }
    }

    fun deleteContacto(){
        val contactos = getContactosUseCase()
        if (contactos.size > 1) { //TODO no se si poner que haya un contacto mínimo o que si borra el último se quede la plantilla
            val contacto = contactos[indice]
            deleteContactoUseCase(contacto)
            if (indice != 0) indice-- else indice = 0
            _uiState.value = MainState(contacto = contactos[indice])
        } else {
            _uiState.value = MainState(error = "No contacts available")
        }

    }
    fun getContactos(size: Int): List<Contacto> {
        val contactos = getContactosUseCase()

        if (contactos.size < size || size < 0)
            _uiState.value = _uiState.value?.copy(error = "error")
        else
            _uiState.value = _uiState.value?.copy(contacto = contactos[size])
        return contactos
    }

    fun pasarContacto(incremento: Int) {
        val contactos = getContactosUseCase()

        if (contactos.isNotEmpty()) {
            indice += incremento
            if (indice < 0) {
                indice = contactos.size - 1
            } else if (indice >= contactos.size) {
                indice = 0
            }
            _uiState.value = MainState(contacto = contactos[indice])
        } else {
            _uiState.value = MainState(error = "No contacts available")
        }
    }

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(error = null)
    }
}

class MainViewModelFactory(
    private val stringProvider: StringProvider,
    private val addContactoUseCase: AddContactoUseCase,
    private val deleteContactoUseCase: DeleteContactoUseCase,
    private val getContactos: GetContactos,
    ): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(stringProvider,
                    addContactoUseCase,
                    deleteContactoUseCase,
                    getContactos) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}