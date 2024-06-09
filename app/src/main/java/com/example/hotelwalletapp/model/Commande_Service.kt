package com.example.hotelwalletapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class Commande_Service(

    val id: String = UUID.randomUUID().toString(),
    val services:  Service,
    val serviceId:  String,
    val command: Command?,
    val commandId: String,

    ):Parcelable
