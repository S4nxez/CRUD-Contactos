package com.example.myapplication.ui.pantallaListado

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.databinding.ContactoViewBinding
import com.example.myapplication.domain.model.Contacto

class ContactoItemViewholder (itemView: View,
                             private val actions:ContactoAdapter.ContactosActions,
    ) :

    RecyclerView.ViewHolder(itemView){

    private val binding = ContactoViewBinding.bind(itemView)

    fun bind(item: Contacto){
        with(binding) {
            textViewNombre.text = item.nombre
            imagenCara.load(item.foto) {
                transformations(CircleCropTransformation())
                crossfade(true)
                size(50, 50)
            }

            itemView.setBackgroundResource(android.R.color.white)
            itemView.setOnLongClickListener{
                true
            }
            itemView.setOnClickListener {
                actions.onItemClick(item)
            }
        }
    }
}
