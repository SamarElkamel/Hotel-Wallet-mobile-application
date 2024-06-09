package com.example.hotelwalletapp.Token

import com.example.hotelwalletapp.model.Client

object AuthClient {
    var client: Client? = null

    private var instance: AuthClient? = null

    fun getInstance(): AuthClient {
        if (instance == null) {
            instance = AuthClient
        }
        return instance!!
    }

    fun logout() {

        client = null

    }
}