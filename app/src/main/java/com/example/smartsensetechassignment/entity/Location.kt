package com.example.smartsensetechassignment.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Location")
class Location(var latitude:String,
                var longitude:String):Serializable {

    @PrimaryKey(autoGenerate = true)
    var timeOfCheckin:Long = 0

}