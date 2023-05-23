package com.example.tripplnr.navigationscreens.Home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.currencyData
import com.example.tripplnr.navigationscreens.Home.dataclass.homeItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrencyRecyclerAdapter(var list: MutableList<currencyData> ):RecyclerView.Adapter<CurrencyRecyclerAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.currency_item,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: InnerClass, position: Int) {



        GlobalScope.launch {
            holder.bind(list)
            }
        }


    class InnerClass(view: View):RecyclerView.ViewHolder(view) {


        var currebcyTextView = view.findViewById<TextView>(R.id.currebcyTextView)


      //  var learnmoretxt = view.findViewById<TextView>(R.id.learnmoretxt)

       suspend fun bind (list: List<currencyData>){
            var item = list[position]


           currebcyTextView.setText(item.currency)


        }

    }
}

