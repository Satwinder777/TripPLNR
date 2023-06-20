package com.example.tripplnr.navigationscreens.Search.hotel.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.ActivityHotelList2Binding
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Search.adapter.RecyclerAdapterSearchFr
import com.example.tripplnr.navigationscreens.Search.hotel.activity.adapter.HotelList2Adapter
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.google.android.material.bottomsheet.BottomSheetDialog

class HotelList2Activity : AppCompatActivity(),HotelList2Adapter.onclickViewDeal {
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
}