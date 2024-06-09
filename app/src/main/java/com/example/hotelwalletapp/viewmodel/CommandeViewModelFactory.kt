package com.example.hotelwalletapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.Repository.CommandeRepository

class CommandeViewModelFactory  (private val commandeRepository: CommandeRepository, private val application: Application):
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommandeViewModel::class.java)) {
            return CommandeViewModel(commandeRepository, application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}