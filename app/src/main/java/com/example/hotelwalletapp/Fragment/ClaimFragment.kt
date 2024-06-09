package com.example.hotelwalletapp.Fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.Repository.ReclamationRepository
import com.example.hotelwalletapp.databinding.ReclamationBinding
import com.example.hotelwalletapp.model.Reclamation
import com.example.hotelwalletapp.viewmodel.ClientViewModel
import com.example.hotelwalletapp.viewmodel.ClientViewModelFactory
import com.example.hotelwalletapp.viewmodel.ReclamationViewModel
import com.example.hotelwalletapp.viewmodel.ReclamationViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class ClaimFragment : Fragment() {

    private lateinit var binding: ReclamationBinding
    private lateinit var reclamationViewModel: ReclamationViewModel
    private lateinit var clientViewModel: ClientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ReclamationBinding.inflate(layoutInflater)
        reclamationViewModel = ViewModelProvider(  this,
        ReclamationViewModelFactory(
            ReclamationRepository(APIService.getService()),
            requireActivity().application
        )
        ).get(ReclamationViewModel::class.java)

        clientViewModel = ViewModelProvider(
            this,
            ClientViewModelFactory(
                ClientRepository(APIService.getUpdatedService()),
                requireActivity().application
            )
        ).get(ClientViewModel::class.java)


        clientViewModel.client.observe(viewLifecycleOwner, { client ->

            val connectedUserId = client.id
            binding.button1.setOnClickListener {
                val title = binding.Title.text.toString()
                val claim = binding.Claim.text.toString()
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                val reclamation = Reclamation(
                    titre = title,
                    text = claim,
                    date = date,
                    clientId = connectedUserId
                )

                reclamationViewModel.createCommand(reclamation)
                val snackbar = Snackbar.make(binding.root, "Your claim has been sent", Snackbar.LENGTH_SHORT)
                val view = snackbar.view
                val params = view.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.CENTER
                view.layoutParams = params
                view.setBackgroundColor(ContextCompat.getColor(requireContext(), androidx.appcompat.R.color.material_deep_teal_200))
                snackbar.show()
            }
        })


        binding.button2.setOnClickListener()
        {
            binding.Claim.text.clear()
            binding.Title.text?.clear()
        }


        return binding.root
    }


}
