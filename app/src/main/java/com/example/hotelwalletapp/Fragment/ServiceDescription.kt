package com.example.hotelwalletapp.Fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelwalletapp.Adapter.AvisAdapter
import com.example.hotelwalletapp.Adapter.ImageLoader
import com.example.hotelwalletapp.databinding.ServicesdetailsBinding
import com.example.hotelwalletapp.model.Avis
import com.example.hotelwalletapp.model.Service
import com.example.hotelwalletapp.model.ServicesWithAvis

class ServiceDescription : Fragment()  {
    private lateinit var binding: ServicesdetailsBinding
    private lateinit var serviceWithAvis: Service
    private lateinit var service: Service
    private lateinit var avisList: List<Avis>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            serviceWithAvis = it.getParcelable<Service>("servicesWithAvis")!!
            avisList = serviceWithAvis.avis
           // service = servicesWithAvis.Service
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ServicesdetailsBinding.inflate(layoutInflater)
        ImageLoader.loadImage(binding.imageview, serviceWithAvis.ImageUrl)
            binding.tv1.text = serviceWithAvis.Libelle
        binding.tv3.text = serviceWithAvis.Description
        binding.price.text=serviceWithAvis.getPriceAsString()
        val avisRecyclerView = binding.recyclerview
        avisRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val avisAdapter = AvisAdapter(avisList)
        avisRecyclerView.adapter = avisAdapter
        return binding.root
    }

    companion object {
        fun newInstance(servicesWithAvis: ServicesWithAvis): ServiceDescription {
            val args = Bundle().apply {
                putParcelable("servicesWithAvis", servicesWithAvis)
            }
            val fragment = ServiceDescription()
            fragment.arguments = args
            return fragment
        }
    }
}




