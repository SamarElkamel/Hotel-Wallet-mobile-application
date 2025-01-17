package com.example.hotelwalletapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Repository.ServicesRepository

class ServicesviewModelFactory constructor(private val repository: ServicesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ServicesViewModel::class.java)) {
            ServicesViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}