package com.example.hotelwalletapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.databinding.ContentprofileBinding
import com.example.hotelwalletapp.viewmodel.*


class ProfileFragment : Fragment()  {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var ViewModel: ClientViewModel
    private lateinit var loginViewModel:LoginViewModel
    private lateinit var binding: ContentprofileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = ContentprofileBinding.inflate(layoutInflater)
        ViewModel = ViewModelProvider(
            this,
            ClientViewModelFactory(
                ClientRepository(APIService.getUpdatedService()),
                requireActivity().application
            )
        ).get(ClientViewModel::class.java)
        loginViewModel = ViewModelProvider(
            this,
            LoginActivityViewModelFactory(
                ClientRepository(APIService.getUpdatedService()),
                requireActivity().application
            )
        ).get(LoginViewModel::class.java)


        ViewModel.client.observe(viewLifecycleOwner) { client ->
            binding.name.tv1.text = client.name
            binding.name.tv2.text = client.phone
            binding.name.tv3.text = client.email
            binding.credit.text = client.credit.toString()+"DT"
        }

        binding.name.but.setOnClickListener()
        {
            val fragment = SliderFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()

        }



        return binding.root
    }


}










