package com.example.hotelwalletapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelwalletapp.Repository.ServicesRepository
import com.example.hotelwalletapp.model.Service
import retrofit2.Call
import retrofit2.Response

class ServicesViewModel (private val repository: ServicesRepository) : ViewModel() {
    val serviceList = MutableLiveData<List<Service>>()
    val errorMessage = MutableLiveData<String>()
    private val _chambreServiceList = MutableLiveData<List<Service>>()
    private val _spaServiceList = MutableLiveData<List<Service>>()
    private val _restaurantServiceList = MutableLiveData<List<Service>>()
    val chambreServiceList: LiveData<List<Service>> = _chambreServiceList
    val spaServiceList: LiveData<List<Service>> = _spaServiceList
    val restaurantServiceList: LiveData<List<Service>> = _restaurantServiceList


    fun getAllServices() {
        val response = repository.getAllServices()
        response.enqueue(object : retrofit2.Callback<List<Service>> {
            override fun onResponse(
                call: Call<List<Service>>,
                response: Response<List<Service>>
            ) {

                serviceList.postValue(response.body())
               /* _chambreServicesList.postValue(filterServicesByType(servicesList, "Chambre"))
                _spaServicesList.postValue(filterServicesByType(servicesList, "SPA"))
                _restaurantServicesList.postValue(filterServicesByType(servicesList, "Restaurant"))*/
            }

            override fun onFailure(call: Call<List<Service>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })


    }

    private fun filterServicesByType(services: List<Service>?, type: String): List<Service> {
        return services?.filter { it.Typeserv== type } ?: emptyList()
    }
}