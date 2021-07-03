package com.example.smartsensetechassignment.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.smartsensetechassignment.DAO.LocationDao
import com.example.smartsensetechassignment.Database.LocationDatabase
import com.example.smartsensetechassignment.Repository.LocationRepository
import com.example.smartsensetechassignment.entity.LocationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(application: Application): AndroidViewModel(application)  {

    val allLocationData: LiveData<List<LocationEntity>>
    private val locationRepository:LocationRepository

    init {
        val locationDatabaseDao: LocationDao = LocationDatabase.BuildTheLocationDatabase(application).getLocationDataDao()
        locationRepository=LocationRepository(locationDatabaseDao)
        allLocationData=locationRepository.locationData
    }

    fun insertLocationData(location: LocationEntity) = viewModelScope.launch (Dispatchers.IO) {
        locationRepository.insertLocationData(location)
    }

}