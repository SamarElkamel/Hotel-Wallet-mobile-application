package com.example.hotelwalletapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelwalletapp.databinding.AvisBinding
import com.example.hotelwalletapp.model.Avis

class AvisAdapter (private val avisList: List<Avis>) : RecyclerView.Adapter<AvisAdapter.AvisViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvisViewHolder {
        val binding = AvisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AvisViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AvisViewHolder, position: Int) {
        holder.bind(avisList[position])
    }

    override fun getItemCount(): Int {
        return avisList.size
    }

    class AvisViewHolder(private val binding: AvisBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(avis: Avis) {

            binding.note.text = avis. getNoteAsString()
            binding.text2.text = avis.Titre
            binding.text3.text = avis.Date
            binding.text4.text = avis.Description

        }
    }

}