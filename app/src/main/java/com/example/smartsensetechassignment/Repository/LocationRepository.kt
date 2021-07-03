package com.example.smartsensetechassignment.Repository

import androidx.lifecycle.LiveData
import com.example.smartsensetechassignment.DAO.LocationDao
import com.example.smartsensetechassignment.entity.LocationEntity

class LocationRepository(private val locationDao: LocationDao) {

    val locationData:LiveData<List<LocationEntity>> = locationDao.getLocationData()

    suspend fun insertLocationData(location: LocationEntity){
        locationDao.insertNewLocationData(location)
    }

}