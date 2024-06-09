package com.example.hotelwalletapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.Token.AuthClient
import com.example.hotelwalletapp.Token.AuthToken
import com.example.hotelwalletapp.data.*
import com.example.hotelwalletapp.model.Client
import kotlinx.coroutines.launch

class LoginViewModel (val clientRepository: ClientRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private val client: MutableLiveData<Client> = MutableLiveData()
    val navigateToNextFragment = MutableLiveData<Boolean>()
    private var userLoggedIn: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getIsLoading(): LiveData<Boolean> = isLoading

    fun loginUser(body: LoginBody) {
        viewModelScope.launch {
            clientRepository.LoginUser(body).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        userLoggedIn.value = true
                        isLoading.value = false
                        client.value = it.data.client
                        AuthToken.getInstance(application.baseContext).token = it.data.token
                        AuthClient.getInstance().client = it.data.client
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

    fun getUser(): LiveData<Client> = client
}