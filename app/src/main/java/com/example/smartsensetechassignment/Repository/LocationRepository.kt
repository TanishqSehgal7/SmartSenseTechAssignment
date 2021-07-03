package com.example.smartsensetechassignment.Repository

import androidx.lifecycle.LiveData
import com.example.smartsensetechassignment.DAO.LocationDao
import com.example.smartsensetechassignment.entity.Location

class LocationRepository(private val locationDao: LocationDao) {

    val locationData:LiveData<List<Location>> = locationDao.getLocationData()

    suspend fun insertLocationData(location: Location){
        locationDao.insertNewLocationData(location)
    }

}