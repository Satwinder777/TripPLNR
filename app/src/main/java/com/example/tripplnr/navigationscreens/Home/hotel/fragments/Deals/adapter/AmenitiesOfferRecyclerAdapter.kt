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
import com.example.tripplnr.navigationscreens.Home.dataclass.imanitiesOffer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AmenitiesOfferRecyclerAdapter(var list : List<imanitiesOffer>):RecyclerView.Adapter<AmenitiesOfferRecyclerAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.amenities_offer_item,parent,false)
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


        var offerTextamenities = view.findViewById<TextView>(R.id.offerTextamenities)


       suspend fun bind (list: List<imanitiesOffer>){
            var item = list[position]

           offerTextamenities.setText(item.offerType)
        }

    }
}