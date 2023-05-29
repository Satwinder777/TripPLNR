package com.example.tripplnr.navigationscreens.Repository

import android.util.Log
import com.example.tripplnr.navigationscreens.DataCls.Massage

class TripRepository(var data: Massage)  {
    fun getData(): Any {

        Log.e("test29", "TripRepo: getData() call  the data we get: ${data} ", )
        return 1
    }

    fun saveData() {
        Log.e("test29", "save data call Trip repo : data is: ${data}", )


    }
}