package com.example.hotelwalletapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelwalletapp.model.Command
import com.example.hotelwalletapp.model.Commande_Service
import com.example.hotelwalletapp.model.Service

class SharedViewModel : ViewModel() {
    val factureList = MutableLiveData<MutableList<Command>>(mutableListOf())
    val _myServiceList = MutableLiveData<MutableList<Service>>(mutableListOf())
    val myServiceList: LiveData<MutableList<Service>> get() = _myServiceList
    val panierServiceList = MutableLiveData<List<Service>>(emptyList())
    val _myCommandeServiceList = MutableLiveData<MutableList<Commande_Service>>(mutableListOf())
    val myCommandeServiceList: LiveData<MutableList<Commande_Service>> get() = _myCommandeServiceList
    var pdfFilePath: String? = null
    var hasClickedButton = false
    var isLoggedIn = false
    private val _totalPrice = MutableLiveData<Double>(getTotalPrice())
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun addService(service: Service) {
        val newList = _myServiceList.value ?: mutableListOf()
        newList.add(service)
        _myServiceList.postValue(newList)
        val newCommandeServiceList = _myCommandeServiceList.value ?: mutableListOf()
        val commandeService = Commande_Service(
            services = service ,
            serviceId = service.id,
            command = null ,
            commandId = ""    ,


        )
        newCommandeServiceList.add(commandeService)
        _myCommandeServiceList.postValue(newCommandeServiceList)

    }


    fun getTotalPrice(): Double {
        var totalPrice = 0.0
        myServiceList.value?.forEach {
            totalPrice += it.Prix
        }
        return totalPrice
    }


    fun deleteService(service: Service) {
        val currentList = _myServiceList.value ?: mutableListOf()
        currentList.remove(service)
        _myServiceList.value = currentList

    }

    fun clearServices() {
        _myServiceList.value?.clear()
        _myServiceList.postValue(_myServiceList.value)
        _myCommandeServiceList.value?.clear()
        _myCommandeServiceList.postValue(_myCommandeServiceList.value)
    }

    fun updateTotalPriceAfterDeletion(deletedServicePrice: Double) {
        val totalPrice = getTotalPrice() - deletedServicePrice
        _totalPrice.value = totalPrice
    }

    fun updateTotalPrice() {
        _myServiceList.postValue(_myServiceList.value)
    }

    fun addFacture(facture: Command) {
        val newList = factureList.value ?: mutableListOf()
        newList.add(facture)
        factureList.postValue(newList)
    }

}

