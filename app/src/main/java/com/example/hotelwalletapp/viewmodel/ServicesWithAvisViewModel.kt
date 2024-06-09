package com.example.hotelwalletapp.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelwalletapp.Repository.ServicesRepository
import com.example.hotelwalletapp.model.ServicesWithAvis

//import com.example.hotelwalletapp.model.ServicesWithAvis

class ServicesWithAvisViewModel (repository: ServicesRepository) : ViewModel() {
    val myList = MutableLiveData<List<ServicesWithAvis>>()

}