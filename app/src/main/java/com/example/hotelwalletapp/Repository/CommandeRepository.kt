package com.example.hotelwalletapp.Repository
import com.example.hotelwalletapp.Api.APIConsumer
import retrofit2.Response
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.Api.SimplifiedMessage
import com.example.hotelwalletapp.data.CommandResponse
import com.example.hotelwalletapp.data.RegisterBody
import com.example.hotelwalletapp.data.RequestStatus
import com.example.hotelwalletapp.model.Command

import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody

class CommandeRepository (val consumer : APIConsumer){
    private val apiService = APIService.getService()
    fun createCommand (body: Command) = flow {
        emit(RequestStatus.Waiting)
        try {
            val response = consumer.createFacture(body)

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





   /* suspend fun createCommand(command: Command): Response<CommandResponse> {
        return apiService.createFacture(command)
    }*/



}

