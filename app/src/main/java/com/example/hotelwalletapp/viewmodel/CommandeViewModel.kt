package com.example.hotelwalletapp.viewmodel


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.Repository.CommandeRepository
import com.example.hotelwalletapp.Token.AuthToken
import com.example.hotelwalletapp.data.RegisterBody
import com.example.hotelwalletapp.data.RequestStatus
import com.example.hotelwalletapp.model.Client
import com.example.hotelwalletapp.model.Command
import kotlinx.coroutines.launch

class CommandeViewModel (val commandeRepository: CommandeRepository, val application: Application) :
    ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()


    fun createCommand(body: Command) {
        viewModelScope.launch {
            commandeRepository.createCommand(body).collect {
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




/*  private val repository = CommandeRepository()

    private val _commandCreated = MutableLiveData<Boolean>()
    val commandCreated: LiveData<Boolean> get() = _commandCreated

    fun createCommand(command: Command) {
        viewModelScope.launch {
            try {
                repository.createCommand(command)
                _commandCreated.value = true
            } catch (e: Exception) {
                _commandCreated.value = false

            }
        }
    }*/
}
