package com.example.hotelwalletapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwalletapp.Repository.CommandeRepository
import com.example.hotelwalletapp.Repository.ReclamationRepository
import com.example.hotelwalletapp.data.RequestStatus
import com.example.hotelwalletapp.model.Command
import com.example.hotelwalletapp.model.Reclamation
import kotlinx.coroutines.launch

class ReclamationViewModel  (val reclamationRepository: ReclamationRepository, val application: Application) :
    ViewModel() {
    private var isLoading: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()


    fun createCommand(body: Reclamation) {
        viewModelScope.launch {
            reclamationRepository.createReclamation(body).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        errorMessage.value = it.message
                    }
                }


            }
        }
    }
}