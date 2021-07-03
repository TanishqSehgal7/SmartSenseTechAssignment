package com.example.smartsensetechassignment.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartsensetechassignment.DAO.LocationDao
import com.example.smartsensetechassignment.entity.LocationEntity

@Database(entities = arrayOf(LocationEntity::class), exportSchema = false , version = 2)
abstract class LocationDatabase: RoomDatabase() {

    abstract fun getLocationDataDao(): LocationDao

    companion object{

        @Volatile
        private var INSTANCE: LocationDatabase?=null

        fun BuildTheLocationDatabase(context: Context) : LocationDatabase {

            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,LocationDatabase::class.java
                , "Location"
                ).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
                instance
            }
        }
    }

}