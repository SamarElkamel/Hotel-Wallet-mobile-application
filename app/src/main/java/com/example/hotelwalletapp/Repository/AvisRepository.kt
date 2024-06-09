package com.example.hotelwalletapp.Repository

import com.example.hotelwalletapp.DataBase.AvisDao
import com.example.hotelwalletapp.model.Avis


class AvisRepository  constructor(private val AvisDao: AvisDao) {
    suspend fun getAlldata(): List<Avis> {
        return AvisDao.getAllAvis()
    }

    suspend fun insertAvis(Avis: List<Avis>) {
        AvisDao.insertAvis(Avis)
    }

    suspend fun getAvisForService(serviceId: Int): List<Avis> {
        return AvisDao.getAvisForService(serviceId)
    }
}
