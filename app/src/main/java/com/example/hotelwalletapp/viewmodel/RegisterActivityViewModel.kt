package com.example.hotelwalletapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.Token.AuthToken
import com.example.hotelwalletapp.data.RegisterBody
import com.example.hotelwalletapp.data.RequestStatus
import com.example.hotelwalletapp.model.Client
import kotlinx.coroutines.launch

class RegisterActivityViewModel(val clientRepository: ClientRepository, val application: Application) :
    ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var isUniqueEmail: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var client: MutableLiveData<Client> = MutableLiveData()
    private var userLoggedIn: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    val navigateToNextFragment = MutableLiveData<Boolean>()
    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getIsUniqueEmail(): LiveData<Boolean> = isUniqueEmail
    fun getUser(): LiveData<Client> = client



    fun registerUser(body: RegisterBody) {
        viewModelScope.launch {
            clientRepository.registerUser(body).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        userLoggedIn.value = true
                        isLoading.value = false
                        client.value = it.data.client
                        AuthToken.getInstance(application.baseContext).token = it.data.token
                        navigateToNextFragment.value = true
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