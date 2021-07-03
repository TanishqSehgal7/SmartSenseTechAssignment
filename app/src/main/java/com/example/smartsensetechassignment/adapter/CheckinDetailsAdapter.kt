package com.example.smartsensetechassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartsensetechassignment.R
import com.example.smartsensetechassignment.entity.LocationEntity
import org.w3c.dom.Text

class CheckinDetailsAdapter(private val context: Context) : RecyclerView.Adapter<CheckinDetailsAdapter.CheckinDetailViewHolder>() {

    private var checkDetailList=ArrayList<LocationEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckinDetailViewHolder {
        val viewHolder=CheckinDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.checkin_rv_item,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: CheckinDetailViewHolder, position: Int) {

        val checkin=checkDetailList[position]
        holder.checkinCountTv.text="Check in "+ (position+1).toString() + " at"
        holder.checkInLatTv.text= "Latitude: "+ checkin.latitudeCheckin
        holder.checkInLongTv.text= "Longitude: " + checkin.longitudeCheckin
        holder.checkInTimeTv.text= "Checked in on: " + checkin.timeOfCheckin

    }

    override fun getItemCount(): Int {
       return checkDetailList.size
    }

    fun UpdateCheckinHistoryafterChanges(updatedCheckinList:List<LocationEntity>) {
        checkDetailList.clear()
        checkDetailList=(updatedCheckinList as ArrayList<LocationEntity>).clone() as ArrayList<LocationEntity>
        notifyDataSetChanged()
    }

    class CheckinDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val checkinCountTv:TextView=view.findViewById(R.id.checkinCount)
        val checkInLatTv:TextView=view.findViewById(R.id.histLat)
        val checkInLongTv:TextView=view.findViewById(R.id.histLong)
        val checkInTimeTv:TextView=view.findViewById(R.id.historyTime)

    }


}