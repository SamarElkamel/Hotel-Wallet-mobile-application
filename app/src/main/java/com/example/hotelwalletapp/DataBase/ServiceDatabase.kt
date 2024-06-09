package com.example.hotelwalletapp.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hotelwalletapp.Converter.Converters
import com.example.hotelwalletapp.model.Avis
import com.example.hotelwalletapp.model.Service
@TypeConverters(Converters::class)
@Database(entities = [Service::class , Avis::class ], version = 1)
abstract class ServiceDatabase : RoomDatabase() {

    abstract fun servicesDao(): ServicesDao

    companion object {
        private var INSTANCE: ServiceDatabase? = null

        fun getInstance(context: Context): ServiceDatabase {
            if (INSTANCE == null) {
                synchronized(ServiceDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ServiceDatabase::class.java,
                        "service_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}