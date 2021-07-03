package com.example.smartsensetechassignment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.smartsensetechassignment.R

class LocationHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_history)

        val backBtn:ImageView=findViewById(R.id.back)
        backBtn.setOnClickListener{
            finish()
        }



    }
}