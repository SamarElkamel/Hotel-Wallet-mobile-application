package com.example.hotelwalletapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize

data class Avis (
    @PrimaryKey
    val id:String = UUID.randomUUID().toString(),
    val Titre:String,
    val Description:String,
    val Date: String,
    val Note:Double,
    val serviceId: Int

): Parcelable

{
    fun getNoteAsString(): String {
        return "$Note"
    }


}