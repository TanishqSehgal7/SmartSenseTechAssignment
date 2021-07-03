package com.example.smartsensetechassignment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartsensetechassignment.R
import com.example.smartsensetechassignment.ViewModel.LocationViewModel
import com.example.smartsensetechassignment.adapter.CheckinDetailsAdapter
import com.example.smartsensetechassignment.entity.LocationEntity

class LocationHistoryActivity : AppCompatActivity() {

    lateinit var locationViewModel: LocationViewModel
    lateinit var checkInRecyclerView: RecyclerView
    lateinit var checkinHistoryAdapter: CheckinDetailsAdapter
    lateinit var locationEntity: LocationEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_history)

        val backBtn:ImageView=findViewById(R.id.back)
        backBtn.setOnClickListener{
            finish()
        }

        checkInRecyclerView=findViewById(R.id.checkinHistoryRV)
        checkInRecyclerView.setHasFixedSize(true)
        checkInRecyclerView.layoutManager=LinearLayoutManager(this)
        checkinHistoryAdapter= CheckinDetailsAdapter(this)
        checkInRecyclerView.adapter=checkinHistoryAdapter

        locationViewModel=ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LocationViewModel::class.java)
        locationViewModel.allLocationData.observe(this, { list ->
            list?.let {
                checkinHistoryAdapter.UpdateCheckinHistoryafterChanges(it)
            }
        })

    }
}