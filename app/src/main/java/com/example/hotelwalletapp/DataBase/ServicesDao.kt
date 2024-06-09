package com.example.hotelwalletapp.DataBase

import androidx.room.*
import com.example.hotelwalletapp.model.Service
import com.example.hotelwalletapp.model.ServicesWithAvis



@Dao
interface ServicesDao {
    @Query("SELECT * FROM Services")
    suspend fun getAllServices(): List<Service>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServices(service: Service)

    @Transaction
    @Query("SELECT * FROM Services")
    suspend fun getServicesWithAvis(): List<ServicesWithAvis>


}