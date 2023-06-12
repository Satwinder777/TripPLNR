package com.example.tripplnr.navigationscreens.Home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.currencyData
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrencyRecyclerAdapter(var list: MutableList<currencyData> ,var callfun0:callfun ):RecyclerView.Adapter<CurrencyRecyclerAdapter.InnerClass>() {



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
            holder.apply {
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, list[position].currency, Toast.LENGTH_SHORT).show()
                    Allfun.currencyData.value = list[position].currency
                }
            }
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
    interface callfun{
        fun showcurrency(currency: String)
    }
}

