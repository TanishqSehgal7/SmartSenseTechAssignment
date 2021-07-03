package com.example.smartsensetechassignment.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smartsensetechassignment.entity.Location

@Dao
interface LocationDao {

    @Query("SELECT * FROM Location ORDER BY timeOfCheckin DESC")
    fun getLocationData(): LiveData<List<Location>>

    @Insert
    suspend fun insertNewLocationData(location: Location)
}