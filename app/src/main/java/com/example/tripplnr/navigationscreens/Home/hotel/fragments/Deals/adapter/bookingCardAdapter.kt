package com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.bookingCard
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class bookingCardAdapter(var list: List<bookingCard>, var onclickCard1: onclickCard):RecyclerView.Adapter<bookingCardAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.booking_card,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {
        GlobalScope.launch {
            holder.bind(list)
            holder.apply {
                itemView.setOnClickListener {


                    onclickCard1.cardIsActive(position)
                }
            }
        }

    }
    class InnerClass(view: View):RecyclerView.ViewHolder(view) {

//        var hotelimg = view.findViewById<ImageView>(R.id.hotelimg)
        val currencytypeId = view.findViewById<TextView>(R.id.currencytypeId)
        val roomtype = view.findViewById<TextView>(R.id.roomTypeId)
//        var ratingtxt = view.findViewById<TextView>(R.id.ratingtxt)


      //  var learnmoretxt = view.findViewById<TextView>(R.id.learnmoretxt)

       suspend fun bind (list: List<bookingCard>){
            var item = list[position]


//           item.hotelimg.let { hotelimg.setImageResource(it) }
           currencytypeId.setText(item.currentcy)
           roomtype.setText(item.roomType)
//           ratingtxt.setText(item.ratingtxt)

        }

    }
    interface onclickCard {
        fun cardIsActive(position: Int)
    }
}