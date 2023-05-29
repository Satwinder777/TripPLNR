package com.example.tripplnr.navigationscreens.Home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HotelRecyclerViewAdapter(var list : MutableList<hotelTitle>):RecyclerView.Adapter<HotelRecyclerViewAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.userrecycleritem_2,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {
        GlobalScope.launch {
            holder.bind(list)
        }

        holder.apply {
            var adapter = list[position].list?.let { hotelsInsideRcAdapter(list[0].list as MutableList<hotelchild>) }
            itemRecyclerView.adapter = adapter
            adapter?.notifyDataSetChanged()
        }
    }
    class InnerClass(view: View):RecyclerView.ViewHolder(view) {

//        var exploreImg = view.findViewById<ImageView>(R.id.exploreImg)
        var popularhoteltxt = view.findViewById<TextView>(R.id.popularhoteltxt)
        var itemRecyclerView = view.findViewById<RecyclerView>(R.id.popularhotelInsideRc)

      //  var learnmoretxt = view.findViewById<TextView>(R.id.learnmoretxt)

       suspend fun bind (list: List<hotelTitle>){
            var item = list[position]


           popularhoteltxt.setText(item.popularhoteltxt)


        }

    }
}