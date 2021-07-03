package com.example.smartsensetechassignment.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.smartsensetechassignment.R
import com.example.smartsensetechassignment.ViewModel.LocationViewModel
import com.example.smartsensetechassignment.entity.LocationEntity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG:String="MainActivity"
    private val ERROR_DIAOG_REQUEST:Int=9001
    private val MY_FINE_LOCATION:String=android.Manifest.permission.ACCESS_FINE_LOCATION
    private val MY_CORASE_LOCTION:String=android.Manifest.permission.ACCESS_COARSE_LOCATION
    var isGranted:Boolean=false
    private lateinit var map:GoogleMap
    private val PERMISSION_REQ_CODE=1234
    private lateinit var fusedLocationProviderClient:FusedLocationProviderClient
    lateinit var checkin:FloatingActionButton
    lateinit var latitudeTv:TextView
    lateinit var longitudeTV:TextView
    lateinit var checkinTime:TextView
    lateinit var locationViewModel:LocationViewModel
    lateinit var locationEntity: LocationEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkin=findViewById(R.id.checkin)
        latitudeTv=findViewById(R.id.Latitude)
        longitudeTV=findViewById(R.id.Longitude)
        checkinTime=findViewById(R.id.checkIntime)

        val history:ImageView=findViewById(R.id.LocationHistory)
        history.setOnClickListener {
            val intent=Intent(this, LocationHistoryActivity::class.java)
            startActivity(intent)
        }
        // set up Support Fragment Manager
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // take permissions explicityly from the user
        val permissionCheckArray:Array<String> = arrayOf(MY_CORASE_LOCTION, MY_FINE_LOCATION)

        if (ContextCompat.checkSelfPermission(this,MY_CORASE_LOCTION)
        ==PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, MY_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                isGranted=true
            } else {
                ActivityCompat.requestPermissions(this, permissionCheckArray, PERMISSION_REQ_CODE)
            }
        } else {
            ActivityCompat.requestPermissions(this, permissionCheckArray, PERMISSION_REQ_CODE)
        }

        // initialize viewmodel to store data
        locationViewModel=ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LocationViewModel::class.java)
    }

//    fun CheckIfServiceAvailable() :Boolean {
//        Log.d(TAG,"isServicesAvailable: checking google services version")
//        val checkAvailability=GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
//
//        if (checkAvailability==ConnectionResult.SUCCESS) {
//            // all the things are working fine
//            Log.d(TAG,"isServicesAvailable: Google Play Services Working fine")
//            return true
//        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(checkAvailability)) {
//            // fixable issue
//            Log.d(TAG,"isServicesAvailable: There is an error but we can fix it!")
//            val dialog: Dialog =GoogleApiAvailability.getInstance().getErrorDialog(this,checkAvailability,ERROR_DIAOG_REQUEST)
//            dialog.show()
//        } else {
//            Toast.makeText(this, "Cannot connect to the google maps service!",Toast.LENGTH_SHORT).show()
//        }
//
//        return true
//    }

    override fun onMapReady(googleMap: GoogleMap) {
        Toast.makeText(this,"Click Floating Button to Checkin",Toast.LENGTH_SHORT).show()
        map=googleMap
        map.uiSettings.apply {
            isCompassEnabled=true
            isMyLocationButtonEnabled=true
        }

        checkin.setOnClickListener {
            fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

            if (ContextCompat.checkSelfPermission(this,MY_CORASE_LOCTION)==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,MY_CORASE_LOCTION)==
                PackageManager.PERMISSION_GRANTED) {
                val locationTask=fusedLocationProviderClient.lastLocation
                locationTask.addOnCompleteListener(OnCompleteListener<Location?> { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "onComplete: found location!")
                        val currentLocation = task.result as Location
                        val locationOnMap=LatLng(currentLocation.latitude,currentLocation.longitude)
                        map.addMarker(MarkerOptions().position(locationOnMap).title("My Current Location"))
                        map.moveCamera(CameraUpdateFactory.newLatLng(locationOnMap))
                        latitudeTv.text=currentLocation.latitude.toString()
                        longitudeTV.text=currentLocation.longitude.toString()
                        val calendar: Calendar = Calendar.getInstance()
                        val dateformat:SimpleDateFormat= SimpleDateFormat("MM-dd-yyyy" + "\n" + "HH:mm:ss a")
                        val time:String=dateformat.format(calendar.time)
                        checkinTime.text="Checked in on: " + time

                        locationViewModel.allLocationData.observe(this, androidx.lifecycle.Observer {

                        })
                        locationEntity= LocationEntity(currentLocation.latitude.toString(),currentLocation.longitude.toString())
                        locationEntity.timeOfCheckin=time
                        locationViewModel.insertLocationData(locationEntity)

                    } else {
                        Log.d(TAG, "onComplete: current location is null")
                        Toast.makeText(
                            this,
                            "unable to get current location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        isGranted=false
        when(requestCode) {
            PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED)
                    isGranted==true
            }
        }
    }

}