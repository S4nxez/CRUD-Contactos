package com.example.myapplication.ui.pantallaCrear

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.ActivityCrearBinding
import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.ui.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CrearActivity : Fragment() {

    private var _binding: ActivityCrearBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CrearViewModel by viewModels ()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = ActivityCrearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventos()
        observarViewModel()
    }

    private fun eventos() {
        with(binding) {
            buttonAdd.setOnClickListener {
                viewModel.handleEvent(CrearEvent.AddContacto(Contacto(
                    999,
                    editTextName.editText?.text.toString(),
                    editTextEmail.editText?.text.toString(),
                    radioMale.isChecked,
                    radioFemale.isChecked,)
                ))
            }
        }
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner){ state ->
            state.event?.let { event ->
                when(event){
                    is UiEvent.PopBackStack -> findNavController().navigateUp()
                    is UiEvent.ShowSnackbar -> Toast.makeText(requireContext(), event.message,
                        Toast.LENGTH_SHORT).show()
                }
                viewModel.handleEvent(CrearEvent.ClearEvent)
            }
        }

    }
}