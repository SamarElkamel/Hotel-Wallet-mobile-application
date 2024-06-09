package com.example.hotelwalletapp.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName="Services")
@Parcelize
data class Service(
  @PrimaryKey
  val id:String = UUID.randomUUID().toString(),
  val Libelle:String,
  val Description:String,
  val Etat:String,
  val Prix: Double,
  val ImageUrl:String,
  val Typeserv:String,
  val Note:Float ,
  val avis :List<Avis>,

) : Parcelable

{

  fun getPriceAsString(): String {
    return "$Prix DT"
  }

  fun getNoteAsString(): String {
    return "$Note"
  }



}


