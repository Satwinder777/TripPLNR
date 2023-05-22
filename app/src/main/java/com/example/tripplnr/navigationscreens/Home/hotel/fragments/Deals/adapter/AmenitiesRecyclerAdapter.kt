package com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.ImanitiesData
import com.example.tripplnr.navigationscreens.Home.dataclass.bookingCard
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AmenitiesRecyclerAdapter(var list : List<ImanitiesData>):RecyclerView.Adapter<AmenitiesRecyclerAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.amenities_item,parent,false)
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

        var imaniImg = view.findViewById<ImageView>(R.id.imaniImg)
        var imaniEvent = view.findViewById<TextView>(R.id.imaniEvent)
//        var imaniImageView = view.findViewById<TextView>(R.id.roomTypeId)
//        var ratingtxt = view.findViewById<TextView>(R.id.ratingtxt)


      //  var learnmoretxt = view.findViewById<TextView>(R.id.learnmoretxt)

       suspend fun bind (list: List<ImanitiesData>){
            var item = list[position]


           item.img.let { imaniImg.setImageResource(it) }
           imaniEvent.setText(item.eventText)
//           imaniEvent.setText(item.roomType)
//           ratingtxt.setText(item.ratingtxt)

        }

    }
}