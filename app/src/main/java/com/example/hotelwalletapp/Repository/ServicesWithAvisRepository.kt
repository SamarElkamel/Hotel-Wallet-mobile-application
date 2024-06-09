package com.example.hotelwalletapp.Repository

import com.example.hotelwalletapp.DataBase.ServicesDao
import com.example.hotelwalletapp.model.Service
import com.example.hotelwalletapp.model.ServicesWithAvis


class ServicesWithAvisRepository (private val servicesDao: ServicesDao) {

    suspend fun getAllServicesWithAvis(): List<ServicesWithAvis> {
        return servicesDao.getServicesWithAvis()
    }

    suspend fun insertServices(service: Service) {
        servicesDao.insertServices(service)
    }
}