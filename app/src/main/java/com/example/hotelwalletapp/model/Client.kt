package com.example.hotelwalletapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.hotelwalletapp.Converter.Converters
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Entity
@Parcelize
data class Client(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    val email: String,
    var password : String,
    var phone: String,
    var credit: Float,
    var checkIn: String,
    var nationalite: String,
    var numcin: String,
    val history: MutableList<Command>




    ) : Parcelable
