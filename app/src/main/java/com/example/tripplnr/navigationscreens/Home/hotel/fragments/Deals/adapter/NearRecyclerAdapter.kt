package com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.bookingCard
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Home.dataclass.nearbyData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NearRecyclerAdapter(var list : List<nearbyData>):RecyclerView.Adapter<NearRecyclerAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.nearby_item,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {
        GlobalScope.launch {
            holder.bind(list)
        }

    }
    class InnerClass(view: View):RecyclerView.ViewHolder(view) {

//        var hotelimg = view.findViewById<ImageView>(R.id.hotelimg)
        var placetext = view.findViewById<TextView>(R.id.placetext)
        var distancetxt = view.findViewById<TextView>(R.id.distancetxt)
//        var ratingtxt = view.findViewById<TextView>(R.id.ratingtxt)


      //  var learnmoretxt = view.findViewById<TextView>(R.id.learnmoretxt)

       suspend fun bind (list: List<nearbyData>){
            var item = list[position]


//           item.hotelimg.let { hotelimg.setImageResource(it) }
           distancetxt.setText(item.distance)
           placetext.setText(item.place)
//           ratingtxt.setText(item.ratingtxt)

        }

    }
}