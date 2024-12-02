package com.example.myapplication.ui.pantallaListado


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentListadoBinding
import com.example.myapplication.domain.model.Contacto
import com.example.myapplication.ui.utils.MarginItemDecoration
import com.example.myapplication.ui.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListadoFragment : Fragment() {
    private var _binding : FragmentListadoBinding?= null
    private val binding get() = _binding!!
    private lateinit var adapter: ContactoAdapter

    private val viewModel: ListadoFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            addButton.setOnClickListener {
                findNavController().navigate(R.id.action_listadoFragmentActivity_to_crearFragment)
            }
        }
        configureRecyclerView()
        observarState()

        binding.progressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleEvents(ListadoEvent.GetContactos)
    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.contactos)
            binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            state.event?.let { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    is UiEvent.PopBackStack -> findNavController().navigateUp()
                }
                viewModel.handleEvents(ListadoEvent.ClearEvent)
            }
        }
    }

    private fun configureRecyclerView() {
        adapter = ContactoAdapter(
            actions = object : ContactoAdapter.ContactosActions {
                override fun onItemClick(contacto: Contacto) {
                    findNavController().navigate(ListadoFragmentDirections.actionListadoFragmentActivityToDetalleActivity(contacto.id))
                }
            })

        binding.listaContactos.layoutManager = LinearLayoutManager(requireContext())
        binding.listaContactos.adapter = adapter
        binding.listaContactos.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.margin
                )
            )
        )
    }

}