package com.example.tripplnr.navigationscreens.favorateFragment.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavorateFragmentAdapter(
    var onItemClick1: onItemClick? = null,

    val context: Context,
    var favorateList1: List<travelBlogItem>?,

    ):RecyclerView.Adapter<FavorateFragmentAdapter.InnerClass>() {
    var filterList: MutableList<travelBlogItem>? = favorateList1?.filter { it.isfavorate==true }?.toMutableList()

//    var livedataFavo = favorateList?.value?.toMutableList() ?: mutableListOf()
//    var filter = favorateList?.value?.filter { it.isfavorate ==true }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homerc_item,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
      return favorateList1?.filter { it.isfavorate==true }?.size ?:0
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass,position: Int) {

//        var itemposition = favorateList?.get(position)
        Log.e("filterlist", "onBindViewHolder: $filterList", )

        if (filterList?.get(position)?.isfavorate !=false){

                val pointerList = filterList?.get(position)

//        holder.itemView.setOnClickListener {
//            onItemClick1?.onclickItem(position)
//        }

        GlobalScope.launch {

            filterList?.let { holder.bind(it) }

//            holder.favorateCardBtn.setOnClickListener {
////                onItemClick1?.onfavoratebtnClicks(position)
//            }
            var sizeless:Boolean = true    //true    already setted!!
                // false

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



                favorateBtn.setImageResource(R.drawable.favo_ic)
                        favorateBtn?.setOnClickListener {
                            filterList?.let { it1 ->
                                onItemClick1?.dltBlog(
                                    position,
                                    pointerList,
                                    it1
                                )


                            }

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

      //  var learnmoretxt = view.findViewById<TextView>(R.id.learnmoretxt)

       @SuppressLint("SuspiciousIndentation")
       suspend fun bind (list: MutableList<travelBlogItem>) {
           val item = list.get(position)

                if (item.isfavorate==true){
                    item.exploreImg?.let { exploreImg.setImageResource(it) }
                    placetextuser.setText(item.placetextuser)
                    dateText.setText(item.dateText)
                    viewedTime.setText(item.viewedTime)
                    aboutText.setText(item.aboutText)

                }




       }


    }
    interface onItemClick{
        fun onclickItem(position: Int)
        fun showtext(position: Int)
        fun dltBlog(position: Int, itemposition: travelBlogItem?, filterList: MutableList<travelBlogItem>)
    }
}