package com.example.hotelwalletapp.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServicesWithAvis(
    @Embedded
    val Service: Service,
    @Relation(
        parentColumn = "id",
        entityColumn = "serviceId"
    )
    val avis: List<Avis>
): Parcelable
