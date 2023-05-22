package com.example.tripplnr.navigationscreens.hotelListFragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelListClass
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Hotel_list_recyclerAdapter(var list : List<hotelListClass>):RecyclerView.Adapter<Hotel_list_recyclerAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.hotel_list_recycler_item,parent,false)
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

        var hotelListItemImg = view.findViewById<ImageView>(R.id.hotelListItemImg)
        var hotelname1 = view.findViewById<TextView>(R.id.hotelname1)
        var locationhotel = view.findViewById<TextView>(R.id.locationhotel)



       suspend fun bind (list: List<hotelListClass>){
            var item = list[position]


           item.hotelListItemImg.let { hotelListItemImg.setImageResource(it) }
           hotelname1.setText(item.hotelname1)
           locationhotel.setText(item.locationhotel)
//           ratingtxt.setText(item.ratingtxt)

        }

    }
}