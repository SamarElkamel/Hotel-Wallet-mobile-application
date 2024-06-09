package com.example.hotelwalletapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Repository.ClientRepository

class RegisterActivityViewModelFactory (private val clientRepository: ClientRepository, private val application: Application):
    ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(RegisterActivityViewModel::class.java)) {
           return  RegisterActivityViewModel(clientRepository,application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
