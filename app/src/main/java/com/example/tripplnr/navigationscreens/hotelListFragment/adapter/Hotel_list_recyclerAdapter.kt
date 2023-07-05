package com.example.tripplnr.navigationscreens.hotelListFragment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelListClass
import com.google.android.material.button.MaterialButton
import com.google.maps.model.PlacesSearchResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Hotel_list_recyclerAdapter(var list : List<PlacesSearchResult>, private val details:viewdetail):RecyclerView.Adapter<Hotel_list_recyclerAdapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hotel_list_recycler_item,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {

        GlobalScope.launch {
            holder.bind(list,holder.itemView.context)

        }
        holder.apply {
            itemView.setOnClickListener {
                try {
                    val m_item = list.get(position)
                    details.onCardClicks(it,position, Pair(m_item.name,m_item.vicinity),m_item.rating)
                }catch (e:Exception){
                    Log.e("exp_occur", "onBindViewHolder: ${e.message}", )
                }


            }

            viewdtl.setOnClickListener {
                details.checkDetails(it,position)
            }
        }


    }
    class InnerClass(view: View):RecyclerView.ViewHolder(view) {

        var hotelListItemImg = view.findViewById<ImageView>(R.id.hotelListItemImg)
        var hotelname1 = view.findViewById<TextView>(R.id.hotelname1)
        var nearbyAddress = view.findViewById<TextView>(R.id.nearbyAddress)
        var rating_float = view.findViewById<TextView>(R.id.rating_float)
        var ratingInText = view.findViewById<TextView>(R.id.ratingInText)
        var currencytextView1 = view.findViewById<TextView>(R.id.currencytextView1)
        var viewdtl = view.findViewById<MaterialButton>(R.id.viewDetails)
        var ratingBar = view.findViewById<RatingBar>(R.id.ratingBar3)



        @OptIn(DelicateCoroutinesApi::class)
        fun bind (list: List<PlacesSearchResult>, context:Context){
            val item = list[position]
            var attr = ""


//           item.hotelListItemImg.let { hotelListItemImg.setImageResource(it) }
            nearbyAddress.setText(item.vicinity)
            rating_float.setText(item.rating.toString())
           hotelname1.setText(item.name)

            when(item.rating){
                in 1.0..2.5->{
                    ratingInText.setText("Average")
                    currencytextView1.setText("50 USD")
                }
                in 2.6..3.8->{
                    ratingInText.setText("Good")
                    currencytextView1.setText("80 USD")
                }
                in 3.9..4.6->{
                    ratingInText.setText("Exelent")
                    currencytextView1.setText("150 USD")
                }
                else->{
                    ratingInText.setText("Best")
                    currencytextView1.setText("200 USD")
                }
            }

//           locationhotel.setText(item.vicinity)

            ratingBar.rating = item.rating

           ratingBar.setIsIndicator(true)

//           ratingtxt.setText(item.ratingtxt)
            if (item.photos!=null){

                    attr =  item.photos.get(0).photoReference
                    Log.e("photo_attri", "bind: $attr", )

            }

            var imageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photoreference=$attr&key=AIzaSyBq16ekrXE3LHeDIwu3KDk0O9s-rMjZpqc"
            GlobalScope.launch(Dispatchers.Main) {
                Glide.with(context)
                    .load(imageUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.blog4))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(hotelListItemImg)
            }


        }

    }
    interface viewdetail{
        fun checkDetails(view: View, position: Int)
        fun onCardClicks(view: View,position: Int,placetxt:Pair<String,String>,rating:Float)
    }
}