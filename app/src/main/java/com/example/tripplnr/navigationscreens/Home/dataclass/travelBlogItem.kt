package com.example.tripplnr.navigationscreens.Home.dataclass

import android.widget.Spinner
import android.widget.TextView

data class travelBlogItem(
    var exploreImg:Int? =null,
    var placetextuser:String? =null,
    var dateText: String? =null,
    var viewedTime:String? =null,
    var aboutText:String? =null,
    var isfavorate:Boolean? =false

    )
data class hotelchild(
    val hotelimg :Int,
    val hotelNametxt :String,
    val hotelLocation :String,
    val ratingtxt :String,

)
data class hotelTitle(
    var popularhoteltxt: String,
    var list : List<hotelchild>?,
)

data class hotelListClass(
    var hotelListItemImg :Int,
    var hotelname1 :String,
    var locationhotel :String,
    var rate:Float

)
data class bookingCard(
    val roomType :String,
    val currentcy : String
)

data class ImanitiesData(
    val img :Int,
    val eventText : String
)

data class imanitiesOffer(
    val offerType : String
)

data class nearbyData(
    val place : String,
    val distance : String
)
data class RatingData(
    val ratingEventName: String,
    val Rating: Double
)
data class similarHotel(
    val hotelname: String,
    val placename: String,
    val rate: Double
)

data class reviewData(
    val reviewerName: String,
    val reviewText: String,
    val rate: Double,
)
data class currencyData(
    val currency: String,

)
data class guest_Children(
    val textView: TextView,
    val spinner: Spinner
)



