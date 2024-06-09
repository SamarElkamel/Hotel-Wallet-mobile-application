package com.example.hotelwalletapp.Converter

import androidx.room.TypeConverter
import com.example.hotelwalletapp.model.Avis
import com.example.hotelwalletapp.model.Command
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromAvisList(value: List<Avis>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Avis>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAvisList(value: String): List<Avis> {
        val gson = Gson()
        val type = object : TypeToken<List<Avis>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCommandList(value: List<Command>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Command>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCommandList(value: String): List<Command> {
        val gson = Gson()
        val type = object : TypeToken<List<Command>>() {}.type
        return gson.fromJson(value, type)
    }

}