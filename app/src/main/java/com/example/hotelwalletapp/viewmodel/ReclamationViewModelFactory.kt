package com.example.hotelwalletapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Repository.ReclamationRepository

class ReclamationViewModelFactory  (private val reclamationRepository: ReclamationRepository, private val application: Application):
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReclamationViewModel::class.java)) {
            return ReclamationViewModel(reclamationRepository, application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}