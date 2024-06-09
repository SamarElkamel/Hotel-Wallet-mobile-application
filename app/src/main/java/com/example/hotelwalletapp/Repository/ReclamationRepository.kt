package com.example.hotelwalletapp.Repository

import com.example.hotelwalletapp.Api.APIConsumer
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.Api.SimplifiedMessage
import com.example.hotelwalletapp.data.RequestStatus
import com.example.hotelwalletapp.model.Command
import com.example.hotelwalletapp.model.Reclamation
import kotlinx.coroutines.flow.flow

class ReclamationRepository (val consumer : APIConsumer) {

    fun createReclamation(body: Reclamation) = flow {
        emit(RequestStatus.Waiting)
        try {
            val response = consumer.postClaim(body)

            if (response.isSuccessful) {
                emit((RequestStatus.Success(response.body()!!)))

            } else {

                emit(
                    RequestStatus.Error(
                        SimplifiedMessage.get(
                            response.errorBody()!!.byteStream().reader().readText()
                        )
                    )
                )
            }
        } catch (e: Exception) {
            //emit(RequestStatus.Error(hashMapOf("error" to ( "This mail is already in use"))))
            emit(RequestStatus.Error(hashMapOf("error" to (e.message ?: "Unknown error"))))

        }
    }
}