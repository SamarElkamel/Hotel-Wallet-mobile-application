package com.example.hotelwalletapp.Repository

import com.example.hotelwalletapp.Api.APIConsumer
import com.example.hotelwalletapp.Api.SimplifiedMessage
import com.example.hotelwalletapp.data.*
import com.example.hotelwalletapp.model.Client
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ClientRepository(val consumer : APIConsumer) {

    fun registerUser (body: RegisterBody) = flow {
        emit(RequestStatus.Waiting)
        try {
            val response = consumer.registerUser(body)

            if (response.isSuccessful) {
                emit ((RequestStatus.Success(response.body()!!)))

            } else {

                emit(RequestStatus.Error(SimplifiedMessage.get(response.errorBody()!!.byteStream().reader().readText())))
            }
        } catch (e: Exception) {
            //emit(RequestStatus.Error(hashMapOf("error" to ( "This mail is already in use"))))
            emit(RequestStatus.Error(hashMapOf("error" to (e.message ?: "Unknown error"))))

        }
    }

    fun LoginUser (body: LoginBody) = flow {
        emit(RequestStatus.Waiting)
        try {
            val response = consumer.loginUser(body)
            if (response.isSuccessful) {
                emit ((RequestStatus.Success(response.body()!!)))


            } else {
                emit(RequestStatus.Error(SimplifiedMessage.get(response.errorBody()!!.byteStream().reader().readText())))
            }
        } catch (e: Exception) {
            emit(RequestStatus.Error(hashMapOf("error" to (e.message ?: "Unknown error"))))

        }
    }


    suspend fun updateClient(id: String, client: Client): Flow<RequestStatus<Client>> = flow {
        emit(RequestStatus.Waiting)
        try {
            val response = consumer.updateClient(id, client)
            if (response.isSuccessful) {
                emit(RequestStatus.Success(response.body()!!))
            } else {
                emit(RequestStatus.Error(SimplifiedMessage.get(response.errorBody()!!.byteStream().reader().readText())))
            }
        } catch (e: Exception) {
            emit(RequestStatus.Error(hashMapOf("error" to (e.message ?: "Unknown error"))))
        }
    }
}


