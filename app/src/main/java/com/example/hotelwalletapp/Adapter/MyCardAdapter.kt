package com.example.hotelwalletapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelwalletapp.databinding.MylistBinding
import com.example.hotelwalletapp.model.Service
import com.example.hotelwalletapp.viewmodel.SharedViewModel


class MyCardAdapter(private var serviceList: List<Service>, private val viewModel: SharedViewModel) :
    RecyclerView.Adapter<MyCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(serviceList[position],viewModel)
        holder.binding.deleteImage.setOnClickListener {
            val deletedService = serviceList[position]
            viewModel.deleteService(deletedService)
            serviceList = serviceList.filterIndexed { index, _ -> index != position }
            notifyItemRemoved(position)
            viewModel.updateTotalPrice()
        }
    }

    override fun getItemCount() = serviceList.size

    fun updateData(newData: List<Service>) {
        serviceList = newData


    }

    class ViewHolder( val binding: MylistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Service, viewModel: SharedViewModel) {
            ImageLoader.loadImage(binding.displayImage, item.ImageUrl)
            binding.text1.text=item.Libelle
            binding.text2.text=item.getPriceAsString()
            binding.data=item
            binding.executePendingBindings()

        }


    }

}

