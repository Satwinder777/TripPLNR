package com.example.tripplnr.navigationscreens.Home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import kotlinx.coroutines.DelicateCoroutinesApi

class hotelsInsideRcAdapter(var list : MutableList<hotelchild>, var onItemClick1: TravelBlogAdapter.onItemClick? = null):RecyclerView.Adapter<hotelsInsideRcAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rc_item_3,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {

            holder.bind(list)
            holder.itemView.setOnClickListener { onItemClick1?.onclickItem(
                position,
                null
            ) }


    }
    class InnerClass(view: View):RecyclerView.ViewHolder(view) {

        var hotelimg = view.findViewById<ImageView>(R.id.hotelimg)
        var hotelNametxt = view.findViewById<TextView>(R.id.hotelNametxt)
        var hotelLocation = view.findViewById<TextView>(R.id.hotelLocation)
        var ratingtxt = view.findViewById<TextView>(R.id.ratingtxt)


      //  var learnmoretxt = view.findViewById<TextView>(R.id.learnmoretxt)

        fun bind (list: List<hotelchild>){
            var item = list[position]


           item.hotelimg.let { hotelimg.setImageResource(it) }
           hotelNametxt.setText(item.hotelNametxt)
           hotelLocation.setText(item.hotelLocation)
           ratingtxt.setText(item.ratingtxt)
           R.string.readless

        }

    }
}