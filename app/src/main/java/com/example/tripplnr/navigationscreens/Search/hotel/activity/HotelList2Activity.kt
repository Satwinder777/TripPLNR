package com.example.tripplnr.navigationscreens.Search.hotel.activity

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tripplnr.R
import com.example.tripplnr.databinding.ActivityHotelList2Binding
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Search.hotel.activity.adapter.HotelList2Adapter
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.firestore.GeoPoint
import java.io.IOException


class HotelList2Activity : AppCompatActivity(),HotelList2Adapter.onclickViewDeal,OnMapReadyCallback {
    private lateinit var binding :ActivityHotelList2Binding
    @SuppressLint("MissingInflatedId", "InflateParams")
    private lateinit var searchquery:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelList2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var rc = binding.hotelListRecyclerview2
        var adapter = HotelList2Adapter(hotelData(),this)
        rc.adapter = adapter

        binding.filterbottom.setOnClickListener {

            val bottomSheetFragment = FilterBottomSheet()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            bottomSheetFragment.isCancelable = false

        }
        binding.showList.setOnClickListener {

            onBackPressed()

        }
        binding.closebtnHotelList2.setOnClickListener{
           onBackPressed()
        }
        searchquery = intent.getStringExtra("query").toString()
//        var location_m = getLocationFromAddress(searchquery)
//        Log.e("location_m", "onCreate: location is $location_m", )

        var rangedate = Allfun.dateLiveData.value
        binding.cityname.setText(searchquery)
        binding.daterange.setText(rangedate)


        var guestdateails = Allfun.guestLiveData.value
        var guest = guestdateails?.guest
        var room = guestdateails?.rooms
        binding.roomcountMapFrag.setText(room.toString())
        binding.guestcountMapFragment.setText(guest.toString())



    }
    fun hotelData():List<hotelTitle>{
        val hotelchildData  = listOf<hotelchild>(
            hotelchild(R.drawable.explore2,"Taj Hotel","Amritsar","3.3"),
            hotelchild(R.drawable.exploreimg,"The Bill Gates","America,Us","4.5"),
            hotelchild(R.drawable.explore2,"Punjab Hotel","Amritsar,Punjab","5.9"),
            hotelchild(R.drawable.exploreimg,"Chandighar Hotel","Chandighar, India","4.7"),
            hotelchild(R.drawable.explore2,"Us Hotel","Us,Amercia","4.8"),
        )
        var list  = listOf<hotelTitle>(
            hotelTitle("Top Hotel",hotelchildData),
            hotelTitle("Best Hotel",hotelchildData),
            hotelTitle("Old Hotel",hotelchildData),
            hotelTitle("Gold Hotel",hotelchildData),

            )
        return list
    }

    override fun viewDealHandle(position: Int, view: View) {
        Allfun.openWeb(this)
    }
    override fun onMapReady(p0: GoogleMap) {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)



    }
//    fun getLocationFromAddress(strAddress: String?): GeoPoint? {
//        val coder = Geocoder(this)
//        val address: List<Address>?
//        var p1: GeoPoint? = null
//        try {
//            address = coder.getFromLocationName(strAddress!!, 5)
//            if (address == null) {
//                return null
//            }
//            val location: Address = address[0]
//            location.getLatitude()
//            location.getLongitude()
//            p1 = GeoPoint(
//                (location.getLatitude() * 1E6) as Double,
//                (location.getLongitude() * 1E6) as Double
//            )
//            return p1
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Log.e("satwinder_m", "getLocationFromAddress: ${e.message}", )
//            R.string.chip2
//        }
//        return null
//    }

}