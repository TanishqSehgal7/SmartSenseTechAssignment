package com.example.smartsensetechassignment.activities

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.example.smartsensetechassignment.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG:String="MainActivity"
    private val ERROR_DIAOG_REQUEST:Int=9001

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

//        if (CheckIfServiceAvailable()) {
//
//        }


    }

    fun CheckIfServiceAvailable() :Boolean {
        Log.d(TAG,"isServicesAvailable: checking google services version")
        val checkAvailability=GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)

        if (checkAvailability==ConnectionResult.SUCCESS) {
            // all the things are working fine
            Log.d(TAG,"isServicesAvailable: Google Play Services Working fine")
            return true
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(checkAvailability)) {
            // fixable issue
            Log.d(TAG,"isServicesAvailable: There is an error but we can fix it!")
            val dialog: Dialog =GoogleApiAvailability.getInstance().getErrorDialog(this,checkAvailability,ERROR_DIAOG_REQUEST)
            dialog.show()
        } else {
            Toast.makeText(this, "Cannot connect to the google maps service!",Toast.LENGTH_SHORT).show()
        }

        return true
    }

    override fun onMapReady(p0: GoogleMap) {

    }

}