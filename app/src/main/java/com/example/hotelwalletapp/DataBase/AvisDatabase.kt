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
@Database(entities = [Avis::class , Service::class],version=1)
abstract class AvisDatabase : RoomDatabase() {
    abstract fun AvisDao(): AvisDao


    companion object {
        private var INSTANCE: AvisDatabase? = null

        fun getInstance(context: Context): AvisDatabase {
            if (INSTANCE == null) {
                synchronized(AvisDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AvisDatabase::class.java,
                        "Avis_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}