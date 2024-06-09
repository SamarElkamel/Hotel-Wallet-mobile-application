package com.example.hotelwalletapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Repository.ClientRepository

class LoginActivityViewModelFactory (private val clientRepository: ClientRepository, private val application: Application):
    ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return  LoginViewModel(clientRepository,application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
