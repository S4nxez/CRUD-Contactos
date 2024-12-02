package com.example.myapplication.ui.pantallaListado

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.domain.model.Contacto

class ContactoAdapter(
    val actions: ContactosActions,
    ) : ListAdapter<Contacto, ContactoItemViewholder>(DiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoItemViewholder {
        return ContactoItemViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.contacto_view, parent, false),
        actions,
            )
    }

    override fun onBindViewHolder(holder: ContactoItemViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<Contacto>() {
        override fun areItemsTheSame(oldItem: Contacto, newItem: Contacto): Boolean {
            return oldItem.nombre == newItem.nombre
        }

        override fun areContentsTheSame(oldItem: Contacto, newItem: Contacto): Boolean {
            return oldItem == newItem
        }
    }

    interface ContactosActions{
        fun onItemClick(contacto: Contacto)
    }
}