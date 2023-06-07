package com.example.tripplnr.navigationscreens.Home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.LiveDataVM.Live
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TravelBlogAdapter(
    var list: List<travelBlogItem>,
    var onItemClick1: onItemClick? = null,

    ):RecyclerView.Adapter<TravelBlogAdapter.InnerClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.homerc_item,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {
        var itemposition = list[position]

        val currentblog = travelBlogItem(itemposition.exploreImg,itemposition.placetextuser,itemposition.dateText,itemposition.viewedTime,itemposition.aboutText)




        holder.itemView.setOnClickListener {
            onItemClick1?.onclickItem(position)
        }

        GlobalScope.launch {
            holder.bind(list)
            holder.favorateCardBtn.setOnClickListener {
                onItemClick1?.onfavoratebtnClicks(position)
            }
            var sizeless:Boolean = true    //true    already setted!!


            holder.apply {






                showMoretxt.setOnClickListener {

                    if(sizeless==true){
                        aboutText.maxLines = Int.MAX_VALUE
                        showMoretxt.setText("Show Less..")
                        sizeless = false

                    }
                    else{
                        aboutText.setLines(2)
                        showMoretxt.setText("Show More..")
                        sizeless = true
                    }


                }

                favorateBtn.setOnClickListener {


                            if (itemposition.isfavorate == true){
                                favorateBtn.setImageResource(R.drawable.fav_empty_ic)
                                onItemClick1?.addOrDlt(true,position,favorateBtn,itemposition)
                                itemposition.isfavorate = false



                            }
                            else{
                                favorateBtn.setImageResource(R.drawable.favo_ic)
//                                favorateList.add(currentblog)
                                onItemClick1?.addOrDlt(false, position, favorateBtn, itemposition)
                                itemposition.isfavorate =true
                            }


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
        var favorateBtn = view.findViewById<ImageView>(R.id.addtoFavorateIV)


       suspend fun bind (list: List<travelBlogItem>){
            var item = list[position]

           if (item.isfavorate==true){
               favorateBtn.setImageResource(R.drawable.favo_ic)
           }else{
               favorateBtn.setImageResource(R.drawable.fav_empty_ic)
           }
           item.exploreImg?.let { exploreImg.setImageResource(it) }
            placetextuser.setText(item.placetextuser)
            dateText.setText(item.dateText)
            viewedTime.setText(item.viewedTime)
            aboutText.setText(item.aboutText)
        }



    }
    interface onItemClick{
        fun onclickItem(position: Int)
        fun onfavoratebtnClicks(position: Int)

        fun addOrDlt(
            like: Boolean,
            position: Int,
            favorateBtn: ImageView,
            itemposition: travelBlogItem
        )



    }
}