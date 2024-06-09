package com.example.hotelwalletapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Repository.ServicesRepository

class ServiceswithAvisviewModelFactory constructor(private val repository: ServicesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ServicesWithAvisViewModel::class.java)) {
            ServicesWithAvisViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}