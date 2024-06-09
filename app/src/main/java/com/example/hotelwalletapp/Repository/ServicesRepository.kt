package com.example.hotelwalletapp.Repository


import com.example.hotelwalletapp.Api.RetrofitService



class ServicesRepository constructor(private val retrofitService: RetrofitService) {
    fun getAllServices() = retrofitService.getAllServices()

}


