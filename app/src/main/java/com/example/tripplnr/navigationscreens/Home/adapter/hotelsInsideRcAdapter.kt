package com.example.tripplnr.navigationscreens.Home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class hotelsInsideRcAdapter(var list : List<hotelchild>):RecyclerView.Adapter<hotelsInsideRcAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rc_item_3,parent,false)
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

        var hotelimg = view.findViewById<ImageView>(R.id.hotelimg)
        var hotelNametxt = view.findViewById<TextView>(R.id.hotelNametxt)
        var hotelLocation = view.findViewById<TextView>(R.id.hotelLocation)
        var ratingtxt = view.findViewById<TextView>(R.id.ratingtxt)


      //  var learnmoretxt = view.findViewById<TextView>(R.id.learnmoretxt)

       suspend fun bind (list: List<hotelchild>){
            var item = list[position]


           item.hotelimg.let { hotelimg.setImageResource(it) }
           hotelNametxt.setText(item.hotelNametxt)
           hotelLocation.setText(item.hotelLocation)
           ratingtxt.setText(item.ratingtxt)

        }

    }
}