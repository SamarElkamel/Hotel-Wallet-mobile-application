package com.example.hotelwalletapp.Api

import com.example.hotelwalletapp.data.*
import com.example.hotelwalletapp.model.Client
import com.example.hotelwalletapp.model.Command
import com.example.hotelwalletapp.model.Reclamation
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface APIConsumer {

    @POST("api/client")
    suspend fun registerUser(@Body body:RegisterBody): Response<RegisterResponse>

    @POST("api/client/login")
    suspend fun loginUser (@Body body: LoginBody): Response<RegisterResponse>

    @PUT("api/client/{id}")
    suspend fun updateClient(@Path("id") id: String, @Body client: Client): Response<Client>

    @POST("api/command")
    suspend fun createFacture(@Body facture: Command): Response<Command>

    @POST("api/reclamation")
    suspend fun postClaim (@Body reclamation: Reclamation): Response<Reclamation>

}
