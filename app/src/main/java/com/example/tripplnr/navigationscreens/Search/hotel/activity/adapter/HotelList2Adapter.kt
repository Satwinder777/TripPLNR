package com.example.tripplnr.navigationscreens.Search.hotel.activity.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HotelList2Adapter(var list : List<hotelTitle>,var m_onclickViewDeal:onclickViewDeal):RecyclerView.Adapter<HotelList2Adapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.hotel_list_2_item,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {
        GlobalScope.launch {
//            holder.bind(list)
        }
        holder.m_button.setOnClickListener {
            m_onclickViewDeal.viewDealHandle(position,it)
        }

//        holder.apply {
//            var adapter = list[position].list?.let { hotelsInsideRcAdapter(it) }
//            itemRecyclerView.adapter = adapter
//            adapter?.notifyDataSetChanged()
//
//        }
    }
    class InnerClass(view: View):RecyclerView.ViewHolder(view) {
        var m_button = view.findViewById<MaterialButton>(R.id.viewDeals)

//        var popularhoteltxt = view.findViewById<TextView>(R.id.popularhoteltxt)
//        var itemRecyclerView = view.findViewById<RecyclerView>(R.id.popularhotelInsideRc)
//       suspend fun bind (list: List<hotelTitle>){
//            var item = list[position]
//           popularhoteltxt.setText(item.popularhoteltxt)


//        }

    }
    interface onclickViewDeal{
        fun viewDealHandle(position: Int, view: View)
    }
}