package com.example.tripplnr.navigationscreens.Search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.adapter.hotelsInsideRcAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecyclerAdapterSearchFr(var list : List<hotelTitle>):RecyclerView.Adapter<RecyclerAdapterSearchFr.InnerClass>() {



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
            holder.bind(list,holder.itemView.context)
        }

        holder.apply {
            var listhotelchild = list.get(0).list
            var adapter = hotelsInsideRcAdapter(listhotelchild as MutableList<hotelchild>)
            itemRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }
    class InnerClass(view: View):RecyclerView.ViewHolder(view) {

        var popularhoteltxt = view.findViewById<TextView>(R.id.popularhoteltxt)
        var seeAllPopularHotel = view.findViewById<TextView>(R.id.seeAllPopularHotel)
        var itemRecyclerView = view.findViewById<RecyclerView>(R.id.popularhotelInsideRc)
       suspend fun bind (list: List<hotelTitle>,context:Context){
            var item = list[position]
           popularhoteltxt.setText(item.popularhoteltxt)
           seeAllPopularHotel.setOnClickListener { Toast.makeText(context, "under Development", Toast.LENGTH_SHORT).show() }


        }

    }
}