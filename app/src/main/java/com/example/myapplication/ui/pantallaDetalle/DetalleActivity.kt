package com.example.myapplication.ui.pantallaDetalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myapplication.databinding.ActivityDetalleBinding
import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.ui.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleActivity : Fragment() {

    private var _binding: ActivityDetalleBinding? = null
    private val binding get() = _binding!!
    private val args : DetalleActivityArgs by navArgs()

    private val viewModel: DetalleViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = ActivityDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleEvent(DetalleEvent.GetContactoByNombre(args.id))

        eventos()
        observarViewModel()
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.event?.let {event ->
                when (event){
                    is UiEvent.ShowSnackbar -> Toast.makeText(requireContext(), event.message,
                        Toast.LENGTH_SHORT).show()
                    is UiEvent.PopBackStack -> findNavController().navigateUp()
                }
                viewModel.handleEvent(DetalleEvent.ClearEvent)
            }
            if (state.isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
            state.contacto?.let { contacto ->
                with (binding){
                    Title.text = contacto.nombre
                    editTextName.editText?.setText(contacto.nombre)
                    editTextEmail.editText?.setText(contacto.job)
                    radioMale.isChecked = contacto.radioEl
                    radioFemale.isChecked = contacto.radioElla
                    editTextActor.editText?.setText(contacto.actor)
                    imagenCara.load(contacto.foto) {
                        crossfade(true)
                        size(300, 300)
                    }
                }
            }
        }
    }

    private fun eventos() {
        with(binding) {
            buttonAdd.setOnClickListener {
                viewModel.handleEvent(DetalleEvent.AddContacto(
                    Contacto(
                        nombre = editTextName.editText?.text.toString(),
                        radioEl = radioMale.isChecked,
                        radioElla = radioFemale.isChecked,
                        job = editTextEmail.editText?.text.toString(),
                        actor = editTextActor.editText?.text.toString(),
                        )
                ))
            }
            buttonDelete.setOnClickListener {
                viewModel.handleEvent(DetalleEvent.DeleteContacto(id = args.id))
            }
            editButton.setOnClickListener {
                viewModel.handleEvent(DetalleEvent.UpdateContacto(
                    Contacto(
                        id = args.id,
                        nombre = editTextName.editText?.text.toString(),
                        radioEl = radioMale.isChecked,
                        radioElla = radioFemale.isChecked,
                        job = editTextEmail.editText?.text.toString(),
                        actor = editTextActor.editText?.text.toString(),
                    )
                ))
            }
        }
    }
}