package com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SimilarHotelAdapter(var list: List<similarHotel>) :
    RecyclerView.Adapter<SimilarHotelAdapter.InnerClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.similarhotel_item, parent, false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {
        var item = list[position]
        GlobalScope.launch {
            holder.bind(list)
        }
//        val rate = item.Rating*10
//        holder.RatingBar.progress = rate.toInt()
//        csvd

    }

    class InnerClass(view: View) : RecyclerView.ViewHolder(view) {
        var hotelName = view.findViewById<TextView>(R.id.hotelName)
        var placeName1 = view.findViewById<TextView>(R.id.placeName1)
        var ratetxt = view.findViewById<TextView>(R.id.ratetxt)
//        var RatingBar = view.findViewById<ProgressBar>(R.id.progressBarRating)

        suspend fun bind(list: List<similarHotel>) {
            var item = list[position]

            hotelName.setText(item.hotelname)
            placeName1.setText(item.placename)
            ratetxt.setText("${item.rate}")

        }

    }
}