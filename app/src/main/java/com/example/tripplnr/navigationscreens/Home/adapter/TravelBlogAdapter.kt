package com.example.tripplnr.navigationscreens.Home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.LiveDataVM.Live
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TravelBlogAdapter(
    var list: MutableList<travelBlogItem>,
    var onItemClick1: onItemClick? = null,
    var favorateList: MutableLiveData<MutableList<travelBlogItem>>,
    private val lifecycleOwner: LifecycleOwner,

):RecyclerView.Adapter<TravelBlogAdapter.InnerClass>() {

    val currentList = favorateList.value?.toMutableList() ?: mutableListOf()




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
//for (i in list){
//    if (list[i].isfavorate == true){
//        Log.e("condcheck", "onBindViewHolder: $position", )
//    }
//}
       val mList = list.filter { it.isfavorate==true }

        Log.e("condcheck", "onBindViewHolder: ${mList}", )






//        if () {
//
//            holder.favorateBtn.setImageResource(R.drawable.favo_ic)
//            ml.add(currentblog)
//        }
//                else  {
//                    Log.e("test3232", "else branch when : ${itemposition.isfavorate}", )
//                }


        Live.data.postValue(mList as MutableList<travelBlogItem>)
        Log.e("mldata", "onBindViewHolder: ml :${Live.data1}", )
        holder.itemView.setOnClickListener {
            onItemClick1?.onclickItem(position)
        }

        GlobalScope.launch {
            holder.bind(list)
            holder.favorateCardBtn.setOnClickListener {
                onItemClick1?.onfavoratebtnClicks(position)
            }
            var sizeless:Boolean = true    //true    already setted!!
                // false


            holder.apply {

                onItemClick1?.addifFAv(itemposition,favorateBtn,currentblog,position,list)




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

                favorateBtn.setOnClickListener {

                            if (itemposition.isfavorate == true){
                                favorateBtn.setImageResource(R.drawable.fav_empty_ic)
                                Log.e(TAG, "onBindViewHolder: true call", )
                                currentList.remove(currentblog)
                                itemposition.isfavorate = false

                            }
                            else{
                                favorateBtn.setImageResource(R.drawable.favo_ic)
                                currentList.add(currentblog)
                                itemposition.isfavorate =true
                                Log.e(TAG, "onBindViewHolder: false call", )
                            }

                            favorateList.value = currentList
                            Live.data1 = currentList

//                            Log.e(TAG, "favorateList : ${favorateList.value} "   , )
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
        fun addifFAv(
            itemposition: travelBlogItem,
            favorateBtn: ImageView,
            currentblog: travelBlogItem,
            position: Int,
            list: MutableList<travelBlogItem>
        )
    }
}