package com.example.tripplnr.navigationscreens.Home.dataclass

data class homeItem(
    var exploreImg :Int,
    var placetextuser :String,
    var dateText :String,
    var viewedTime :String,
    var aboutText :String,

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



