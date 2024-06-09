package com.example.hotelwalletapp.data

import com.example.hotelwalletapp.model.Commande_Service
import java.util.*

data class CommandResponse (

    val id: String = UUID.randomUUID().toString(),
    val type: String,
    val num: String,
    val totalPrice: Double,
    val Date: String,
    val clientId: String?,
    val services: List<Commande_Service>,

    )