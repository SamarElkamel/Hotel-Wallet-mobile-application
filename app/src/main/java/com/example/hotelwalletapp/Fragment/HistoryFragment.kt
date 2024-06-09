package com.example.hotelwalletapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelwalletapp.Adapter.HistoryAdapter
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.databinding.HistoriqueBinding
import com.example.hotelwalletapp.databinding.HistoryRecyclerviewBinding
import com.example.hotelwalletapp.model.Command
import com.example.hotelwalletapp.viewmodel.ClientViewModel
import com.example.hotelwalletapp.viewmodel.ClientViewModelFactory


class HistoryFragment : Fragment () {

    private lateinit var binding:HistoryRecyclerviewBinding
    private lateinit var ViewModel: ClientViewModel
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = HistoryRecyclerviewBinding.inflate(layoutInflater)
       // binding.lifecycleOwner = viewLifecycleOwner

        ViewModel = ViewModelProvider(
            this,
            ClientViewModelFactory(
                ClientRepository(APIService.getUpdatedService()),
                requireActivity().application
            )
        ).get(ClientViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyAdapter = HistoryAdapter()
        getHistoryData()

        binding.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }
    }

    private fun getHistoryData() {
        ViewModel.client.observe(viewLifecycleOwner) { client ->
            if (client != null) {
                val historyData = client.history
                historyAdapter.setData(historyData)
            }
        }
    }


}