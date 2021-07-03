package com.example.smartsensetechassignment.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smartsensetechassignment.entity.LocationEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM CheckinLocation ORDER BY timeOfCheckin DESC")
    fun getLocationData(): LiveData<List<LocationEntity>>

    @Insert
    suspend fun insertNewLocationData(location: LocationEntity)
}