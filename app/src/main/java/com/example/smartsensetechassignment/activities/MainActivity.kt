package com.example.smartsensetechassignment.activities

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartsensetechassignment.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG:String="MainActivity"
    private val ERROR_DIAOG_REQUEST:Int=9001
    private val MY_FINE_LOCATION:String=android.Manifest.permission.ACCESS_FINE_LOCATION
    private val MY_CORASE_LOCTION:String=android.Manifest.permission.ACCESS_COARSE_LOCATION
    var isGranted:Boolean=false
    private lateinit var map:GoogleMap
    private val PERMISSION_REQ_CODE=1234


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        Toast.makeText(this,"Map is Loaded",Toast.LENGTH_SHORT).show()
        map=googleMap
        val sydney=LatLng(-34.0,151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Markey in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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