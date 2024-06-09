package com.example.hotelwalletapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelwalletapp.databinding.HistoryBinding
import com.example.hotelwalletapp.model.Command

class HistoryAdapter() :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var history: List<Command> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val command = history[position]
        holder.bind(command)
    }

    override fun getItemCount(): Int {
        return history.size
    }

    fun setData(data: List<Command>) {
        history = data
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: HistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(command: Command) {

            binding.tv1.text = command.Date
            binding.tv2.text = command.type
            binding.tv3.text = command.num
            binding.tv4.text = command.totalPrice.toString()+"DT "
        }
    }
}
