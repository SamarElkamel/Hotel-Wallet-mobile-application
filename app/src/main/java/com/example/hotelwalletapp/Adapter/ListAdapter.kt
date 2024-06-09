package com.example.hotelwalletapp.Adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelwalletapp.Fragment.ServiceDescription
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.databinding.RecylcerviewBinding
import kotlinx.android.synthetic.main.recylcerview.view.*
import java.util.*

class ListAdapter(private val onServiceClickListener: OnServiceClickListener) : RecyclerView.Adapter<ListAdapter.ListViewHolder>(), Filterable  {
    var Datalist = mutableListOf<com.example.hotelwalletapp.model.Service>()
    var filteredDataList: MutableList<com.example.hotelwalletapp.model.Service>? = null
    private val myServiceList: MutableList<com.example.hotelwalletapp.model.Service> = mutableListOf()


    @SuppressLint("NotifyDataSetChanged")
    fun setDataList(Datalist: List<com.example.hotelwalletapp.model.Service>) {

        this.Datalist = Datalist.toMutableList()
        filteredDataList = null
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val dataList = if (filteredDataList != null) filteredDataList else Datalist
        return ListViewHolder(
            RecylcerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            Datalist,
            filteredDataList,
            myServiceList
        )
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {


        val data = if (filteredDataList != null) {
            filteredDataList!![position]
        } else {
            Datalist[position]
        }

        holder.bind(data)

        holder.binding.button1.setOnClickListener {
            val service = if (filteredDataList != null) {
                filteredDataList!![position]
            } else {
                Datalist[position]
            }

            onServiceClickListener?.onServiceClicked(service)
        }

    }

    override fun getItemCount(): Int {
        return if (filteredDataList != null) {
            filteredDataList!!.size
        } else {
            Datalist.size
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(thumbs: ImageView, url: String?) {
            if (url != null) {
                Glide.with(thumbs)
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .fallback(R.drawable.ic_launcher_foreground)
                    .into(thumbs)
            }
        }
    }


    class ListViewHolder(
        val binding: RecylcerviewBinding,
        private val dataList: MutableList<com.example.hotelwalletapp.model.Service>,
        private val filteredDataList: MutableList<com.example.hotelwalletapp.model.Service>?,
        private val myServiceList: MutableList<com.example.hotelwalletapp.model.Service>,

        ) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.cardview1.setOnClickListener {
                val servicesWithAvis = if (filteredDataList != null) {
                    filteredDataList!![adapterPosition]
                } else {
                    dataList[adapterPosition]
                }
                val fragment = ServiceDescription()
                val bundle = Bundle()
                bundle.putParcelable("servicesWithAvis", servicesWithAvis)
                fragment.arguments = bundle
                val activity = itemView.context as AppCompatActivity
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, fragment)
                    .addToBackStack(null)
                    .commit()
            }

        }


        fun bind(Data: com.example.hotelwalletapp.model.Service) {
            binding.data = Data

        }


    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<com.example.hotelwalletapp.model.Service>()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(Datalist)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                    for (item in Datalist) {
                        if (item.Libelle.toLowerCase(Locale.ROOT)
                                .contains(filterPattern)
                        ) {
                            filteredList.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDataList = results?.values as? MutableList<com.example.hotelwalletapp.model.Service>
                notifyDataSetChanged()
            }
        }
    }

    fun sortListByPrice(ascending: Boolean) {
        if (ascending) {
            Datalist.sortBy { it.Prix }
        } else {
            Datalist.sortByDescending { it.Prix }
        }
        notifyDataSetChanged()
    }

    fun resetDataList() {
        filteredDataList = null
        notifyDataSetChanged()
    }

}

