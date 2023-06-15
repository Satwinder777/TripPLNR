package com.example.tripplnr.navigationscreens.hotelListFragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelListClass
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Hotel_list_recyclerAdapter(var list : List<hotelListClass>,private val details:viewdetail):RecyclerView.Adapter<Hotel_list_recyclerAdapter.InnerClass>() {



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
        holder.apply {
            viewdtl.setOnClickListener {
                details.checkDetails(it,position)
            }
        }

    }
    class InnerClass(view: View):RecyclerView.ViewHolder(view) {

        var hotelListItemImg = view.findViewById<ImageView>(R.id.hotelListItemImg)
        var hotelname1 = view.findViewById<TextView>(R.id.hotelname1)
        var locationhotel = view.findViewById<TextView>(R.id.locationhotel)
        var viewdtl = view.findViewById<MaterialButton>(R.id.viewDetails)



       suspend fun bind (list: List<hotelListClass>){
            var item = list[position]


           item.hotelListItemImg.let { hotelListItemImg.setImageResource(it) }
           hotelname1.setText(item.hotelname1)
           locationhotel.setText(item.locationhotel)
//           ratingtxt.setText(item.ratingtxt)

        }

    }
    interface viewdetail{
        fun checkDetails(view: View, position: Int)
    }
}