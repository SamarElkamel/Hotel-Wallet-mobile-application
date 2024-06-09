package com.example.hotelwalletapp.Api

import com.example.hotelwalletapp.model.Service
import retrofit2.http.GET

interface ServiceConsumer {
    @GET("services")
    suspend fun getServices(): List<Service>
}