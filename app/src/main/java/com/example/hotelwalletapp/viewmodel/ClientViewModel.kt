package com.example.hotelwalletapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.Token.AuthClient
import com.example.hotelwalletapp.data.RequestStatus
import com.example.hotelwalletapp.model.Client
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class ClientViewModel(private val repository: ClientRepository , val application: Application) : ViewModel() {

    private val _client = MutableLiveData<Client>()
    val client: LiveData<Client>
        get() = _client

    private val _updateStatus = MutableLiveData<RequestStatus<Client>>()
    val updateStatus: LiveData<RequestStatus<Client>>
        get() = _updateStatus

    init {

        AuthClient.getInstance().client?.let {
            _client.postValue(it)
        }
    }

    fun updateClient(id: String, client: Client) {
        viewModelScope.launch {
            repository.updateClient(id, client)
                .onStart { _updateStatus.value = RequestStatus.Waiting }
                .catch { e ->
                    _updateStatus.value = RequestStatus.Error(
                        hashMapOf("error" to (e.message ?: "Unknown error"))
                    )
                }
                .collect { status ->
                    _updateStatus.value = status
                    if (status is RequestStatus.Success) {
                        _client.value = status.data
                    }
                }
        }
    }
}
