package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.data.Repository
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.domain.useCases.contactos.AddContactoUseCase
import com.example.myapplication.domain.useCases.contactos.DeleteContactoUseCase
import com.example.myapplication.domain.useCases.contactos.GetContactos
import com.example.myapplication.utils.StringProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddContactoUseCase(),
            DeleteContactoUseCase(),
            GetContactos(),
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        eventos()
        observarViewModel()
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this@MainActivity) { state ->
            state.error?.let { error ->
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }
            state.contacto?.let { contacto ->
                with (binding){
                Title.text = contacto.nombre
                editTextName.setText(contacto.nombre)
                editTextEmail.setText(contacto.email)
                editTextPassword.setText(contacto.pwd)
                radioMale.isChecked = contacto.genero
                radioFemale?.isChecked ?: !contacto.genero
                switchBlock?.isChecked = contacto.bloquear
                ratingBarStars.rating = contacto.estrellas
                ratingBarFrequency.progress = contacto.frecuencia.toInt()
                switchSellData.isChecked = contacto.venderDatos
                }
            }
            binding.position.text = "${state.indiceMain + 1} of ${state.tamanyo}"
            binding.buttonNext.isEnabled = !state.isUltimo
            binding.buttonPrevious.isEnabled = !state.isPrimero
        }
    }

    private fun eventos() {
        with(binding) {
            buttonAdd.setOnClickListener {
                viewModel.addContacto(
                    Contacto(
                        editTextName.text.toString(),
                        editTextEmail.text.toString(),
                        editTextPassword.text.toString(),
                        radioGender == radioMale, //TODO REEVISAR QUE ESTO FUNCIONE
                        switchBlock?.isChecked ?: false,
                        ratingBarStars.rating,
                        ratingBarFrequency.progress.toFloat(),
                        switchSellData.isChecked
                    ))
            }
            buttonNext.setOnClickListener {
                viewModel.pasarContacto(1)
            }
            buttonPrevious.setOnClickListener {
                viewModel.pasarContacto(-1)
            }
            buttonDelete.setOnClickListener {
                viewModel.deleteContacto()
            }
        }
    }
}