package com.example.tripplnr.navigationscreens.Search.hotel.activity.adapter

import android.annotation.SuppressLint
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton
import com.google.maps.model.PlacesSearchResult
import kotlinx.coroutines.DelicateCoroutinesApi

class HotelList2Adapter(
    var list: List<PlacesSearchResult>,
    var m_onclickViewDeal: onclickViewDeal,
    val mMap: GoogleMap
):RecyclerView.Adapter<HotelList2Adapter.InnerClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.hotel_list_2_item,parent,false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {

        try {

            holder.bind(list,holder.itemView.context,m_onclickViewDeal,mMap)

        }catch (e:Exception){
            Log.e("exception_test", "onBindViewHolder: ${e.message},${e.cause}", )
        }

        holder.m_button.setOnClickListener {
            m_onclickViewDeal.viewDealHandle(position,it)
        }

//        holder.apply {
//            var adapter = list[position].list?.let { hotelsInsideRcAdapter(it) }
//            itemRecyclerView.adapter = adapter
//            adapter?.notifyDataSetChanged()
//
//        }
    }
    class InnerClass(view: View):RecyclerView.ViewHolder(view) {
        var dollar = 18

        var m_button = view.findViewById<MaterialButton>(R.id.viewDeals)
        var name = view.findViewById<TextView>(R.id.hotel_name_place)
        var address = view.findViewById<TextView>(R.id.locationhotel2)
        var rating_condition = view.findViewById<TextView>(R.id.rating_condition)
        var hotel_rate = view.findViewById<TextView>(R.id.hotel_rating)
        var charges = view.findViewById<TextView>(R.id.charges)
        var rating_in_star = view.findViewById<RatingBar>(R.id.rating_star)
        var hotel_image = view.findViewById<ImageView>(R.id.hotel_Image)

//        var popularhoteltxt = view.findViewById<TextView>(R.id.popularhoteltxt)
//        var itemRecyclerView = view.findViewById<RecyclerView>(R.id.popularhotelInsideRc)

@OptIn(DelicateCoroutinesApi::class)
fun bind (
    list: List<PlacesSearchResult>,
    context: Context,
    m_onclickViewDeal: onclickViewDeal,
    mMap: GoogleMap
){
            var item = list[position]
            var attr = ""
            name.setText(item.name)
            address.setText(item.vicinity)
            rating_in_star.rating = item.rating




    when(item.rating){
      in 1.0..2.5->{
          rating_condition.setText("Average")
      }
      in 2.6..3.8->{
          rating_condition.setText("Good")
      }
      in 3.9..4.6->{
          rating_condition.setText("Exelent")
      }
      else->{
          rating_condition.setText("Best")
      }
    }
    var temp_list  = mutableListOf<PlacesSearchResult>()
    temp_list = list.toMutableList()
//    if (item.geometry.location!=null){
//        try{
//            temp_list.forEach {
//                for ( i in temp_list){
//                    if (it.geometry.location==i.geometry.location){
//                       temp_list.remove(it)
//                    }
//                }
//            }
//        }catch (e:Exception){
//            Log.e("exception_msg", "bind: exceptions :-${e.cause} ${e.message}", )
//        }
////        temp_list.forEach {

////                        Log.e("location_", "setMarker:$location,$name", )
////            Log.e("location_", "bind:---------------------------------------------------------------------- ")
//////            Log.e("temp_list", "bind:$temp_list ", )
////        }
//
        var final_List = temp_list.distinctBy { it.geometry.location }
         final_List.forEach {
             var location = it.geometry.location
             var name  = it.name
             mMap.addMarker(MarkerOptions().position(com.google.android.gms.maps.model.LatLng(location.lat,location.lng)).title(name))
             mMap.animateCamera(CameraUpdateFactory.newLatLng(com.google.android.gms.maps.model.LatLng(location.lat,location.lng)))
             Log.e("final_List", "bind: ${it.geometry.location},${it.name},${final_List.size}", )
         }
//
//
//    }

             rating_in_star.setIsIndicator(true)
             hotel_rate.setText(item.rating.toString())
             charges.setText("$dollar $")
//             hotel_image.setImageURI(Uri.parse(item.icon.toString()))
              dollar+=1

//    Glide.with(context).load(item.icon).into(hotel_image)

            if (item.photos!=null){
                item.photos.forEach {
                   attr =  it.photoReference
                    Log.e("photo_attri", "bind: $attr", )
                }
            }

//    AIzaSyBq16ekrXE3LHeDIwu3KDk0O9s-rMjZpqc
    val imageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photoreference=$attr&key=AIzaSyBq16ekrXE3LHeDIwu3KDk0O9s-rMjZpqc"
//    "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photoreference=Aaw_FcKPlaek9NwNLbRAEHsPAWNoO4BEhh6eIC2jJJDMxhNQwkV81Srp7Vz7I8NhnN-kbhW8Wc5sjQl1ZYOLCdaEHjoFhlB8wqyE-dGFZdW_abnjz77onUiy2kNgNDWXxYPqr-34lCXQyhfK1cl2RPU94fShJgh7EcB2IlJnxIdbl8oPu6EX&key=AIzaSyBq16ekrXE3LHeDIwu3KDk0O9s-rMjZpqc"



     Glide.with(context)
         .load(imageUrl)
         .apply(RequestOptions.placeholderOf(R.drawable.blog4))
         .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
         .into(hotel_image)






        }

    }
    interface onclickViewDeal{
        fun viewDealHandle(position: Int, view: View)
//        fun setMarker(item: List<PlacesSearchResult>)
    }
}