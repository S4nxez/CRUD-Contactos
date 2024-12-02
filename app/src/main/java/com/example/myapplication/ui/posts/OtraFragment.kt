package com.example.myapplication.ui.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentOtraBinding
import com.example.myapplication.domain.model.Post
import com.example.myapplication.ui.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtraFragment : Fragment() {

    private var _binding: FragmentOtraBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OtraViewModel by viewModels ()

    override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos()
        observarViewModel()
    }

    private fun eventos() {
        with(binding) {
            saveButton.setOnClickListener {
                viewModel.handleEvent(
                    OtraEvent.AddPost(
                        Post(
                            999,
                            usernameInput.editText?.text.toString(),
                            emailInput.editText?.text.toString(),
                        )
                    )
                )
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
                viewModel.handleEvent(OtraEvent.ClearEvent)
            }
        }

    }
}