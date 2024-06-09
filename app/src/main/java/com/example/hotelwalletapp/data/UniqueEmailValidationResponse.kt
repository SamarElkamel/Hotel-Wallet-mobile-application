package com.example.hotelwalletapp.data

import com.example.hotelwalletapp.model.Client

data class UniqueEmailValidationResponse (val isUnique: Boolean, val client: Client) {
}