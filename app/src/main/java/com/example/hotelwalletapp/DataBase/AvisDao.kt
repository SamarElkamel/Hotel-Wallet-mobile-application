package com.example.hotelwalletapp.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hotelwalletapp.model.Avis

@Dao
interface AvisDao {
    @Query("SELECT * FROM Avis")
    suspend fun getAllAvis(): List<Avis>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAvis(Avis: List<Avis>)

    @Query("SELECT * FROM Avis WHERE serviceId = :serviceId")
    suspend fun getAvisForService(serviceId: Int): List<Avis>
}