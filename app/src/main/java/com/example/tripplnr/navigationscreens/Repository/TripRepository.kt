package com.example.tripplnr.navigationscreens.Repository

import android.util.Log
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.DataCls.Massage
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem

class TripRepository  {


    fun datatravel(): MutableList<travelBlogItem> {
        var  list  = mutableListOf<travelBlogItem>(
            travelBlogItem(
                R.drawable.explore2,"the Golden Temple","12 may 23 ","1.32s",
                R.string.testLine.toString(), isfavorate = true),

            travelBlogItem(
                R.drawable.exploreimg,"the Royal Temple","12 may 23 ","1.35s",
                R.string.testLine.toString(), isfavorate = false),
            travelBlogItem(
                R.drawable.exploreimg,"the Swanrana mandhir ","12 may 23 ","1.11s",
                R.string.testLine.toString(), isfavorate = true),
            travelBlogItem(
                R.drawable.explore2,"the love city","12 may 23 ","12.32s",
                R.string.testLine.toString(), isfavorate = false),
            travelBlogItem(
                R.drawable.exploreimg,"the Punjaab","12 may 23 ","59.32s",
                R.string.testLine.toString(), isfavorate = true),

            )
 return list
    }
}