package com.example.hotelwalletapp.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*
@Entity
@Parcelize
data class Command(

    val id: String = UUID.randomUUID().toString(),
    val type: String,
    val num: String,
    val totalPrice: Float,
    val Date: String,
    val clientId: String?,
   // val client : Client? ,
    val services: List<Commande_Service>,

    ):Parcelable