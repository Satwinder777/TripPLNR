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
import com.example.tripplnr.navigationscreens.Home.dataclass.homeItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TravelBlogAdapter(var list: MutableList<homeItem>, var onItemClick1: onItemClick?):RecyclerView.Adapter<TravelBlogAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.homerc_item,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: InnerClass, position: Int) {

        holder.itemView.setOnClickListener {
            onItemClick1?.onclickItem(position)
        }

        GlobalScope.launch {
            holder.bind(list)
            holder.favorateCardBtn.setOnClickListener {
                onItemClick1?.onfavoratebtnClicks(position)
            }
            var sizeless:Boolean = true

            holder.apply {

                showMoretxt.setOnClickListener {

                    if(sizeless==true){
                        aboutText.maxLines = Int.MAX_VALUE
//                        onItemClick1?.showtext(position)
                        showMoretxt.setText("Show Less..")
                        sizeless = false

                    }
                    else{
                        aboutText.setLines(2)
                        showMoretxt.setText("Show More..")
//                        onItemClick1?.showtext(position)
                        sizeless = true
                    }

                    onItemClick1?.showtext(position)

                }

            }
        }

    }
    class InnerClass(view: View):RecyclerView.ViewHolder(view) {

        var exploreImg = view.findViewById<ImageView>(R.id.exploreImg)
        var placetextuser = view.findViewById<TextView>(R.id.placetextuser)
        var dateText = view.findViewById<TextView>(R.id.dateText)
        var viewedTime = view.findViewById<TextView>(R.id.viewedTime)
        var aboutText = view.findViewById<TextView>(R.id.aboutText)
        var showMoretxt = view.findViewById<TextView>(R.id.showMoretxt)
        var favorateCardBtn = view.findViewById<CardView>(R.id.favorateCardBtn)

      //  var learnmoretxt = view.findViewById<TextView>(R.id.learnmoretxt)

       suspend fun bind (list: List<homeItem>){
            var item = list[position]

            exploreImg.setImageResource(item.exploreImg)
            placetextuser.setText(item.placetextuser)
            dateText.setText(item.dateText)
            viewedTime.setText(item.viewedTime)
            aboutText.setText(item.aboutText)

        }

    }
    interface onItemClick{
        fun onclickItem(position: Int)
        fun onfavoratebtnClicks(position: Int)
        fun showtext(position: Int)
    }
}