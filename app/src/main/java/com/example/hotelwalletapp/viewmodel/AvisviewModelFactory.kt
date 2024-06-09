package com.example.hotelwalletapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Repository.AvisRepository
import com.example.hotelwalletapp.Repository.ServicesRepository

class AvisviewModelFactory  constructor(private val repository: AvisRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AvisViewModel::class.java)) {
            AvisViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}