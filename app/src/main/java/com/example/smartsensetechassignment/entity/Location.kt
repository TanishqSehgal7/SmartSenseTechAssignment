package com.example.smartsensetechassignment.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

@Entity(tableName = "CheckinLocation")
class LocationEntity(var latitudeCheckin:String,
                var longitudeCheckin:String):Serializable {

    @PrimaryKey(autoGenerate = true)
    var timeOfCheckin:String = ""

}