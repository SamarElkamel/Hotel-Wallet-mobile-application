package com.example.hotelwalletapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelwalletapp.Repository.AvisRepository
import com.example.hotelwalletapp.model.Avis


class AvisViewModel (private val repository: AvisRepository) : ViewModel() {
    val myList = MutableLiveData<List<Avis>>()
    val errorMessage = MutableLiveData<String>()
}