package com.example.tripplnr.navigationscreens.favorateFragment.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavorateFragmentAdapter(
    var onItemClick1: onItemClick? = null,
    var favorateList:  MutableList<travelBlogItem>?=null,
    val context: Context
):RecyclerView.Adapter<FavorateFragmentAdapter.InnerClass>() {

//    var livedataFavo = favorateList.value?.toMutableList() ?: mutableListOf()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.homerc_item,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        var size = favorateList?.size
       return size!!
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {

        var itemposition = favorateList?.get(position)

        holder.itemView.setOnClickListener {
            onItemClick1?.onclickItem(position)
        }

        GlobalScope.launch {
            favorateList.let {
                if (it != null) {
                    holder.bind(it)
                }
            }
            holder.favorateCardBtn.setOnClickListener {
                onItemClick1?.onfavoratebtnClicks(position)
            }
            var sizeless:Boolean = true    //true    already setted!!
                // false

            holder.apply {

                var TAG="favorate"
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
                favorateBtn.setImageResource(R.drawable.favo_ic)
                        favorateBtn?.setOnClickListener {

                            favorateList?.removeAt(position)
                                onItemClick1?.dltBlog(position)
//                            var currentblog = travelBlogItem(itemposition.exploreImg,itemposition.placetextuser,itemposition.dateText,itemposition.viewedTime,itemposition.aboutText)
//                            if (itemposition.isfavorate == true){
//                                favorateBtn.setImageResource(R.drawable.fav_empty_ic)
//                                Log.e(TAG, "onBindViewHolder: true call", )
//                                livedataFavo.remove(currentblog)
//
//                                itemposition.isfavorate = false
//
//                            }
//                           else{
//                                favorateBtn.setImageResource(R.drawable.favo_ic)
//                                livedataFavo.add(currentblog)
//                                itemposition.isfavorate =true
//                                Log.e(TAG, "onBindViewHolder: false call", )
//                            }
//
//
//                            favorateList.value = livedataFavo
//
//                            Log.e(TAG, "favorateList : ${livedataFavo} "   , )
//                            Log.e(TAG, " position : $itemposition,  :${itemposition.isfavorate}", )
//                            Log.e(TAG, "----------------------------------------------------------------------------------------------------", )



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

      //  var learnmoretxt = view.findViewById<TextView>(R.id.learnmoretxt)

       suspend fun bind (list: List<travelBlogItem>){
            var item = list[position]

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
        fun showtext(position: Int)
        fun dltBlog(position: Int?)
    }
}