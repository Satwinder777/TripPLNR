package com.example.tripplnr.navigationscreens.LiveDataVM

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem

object Live {
    val data = MutableLiveData<MutableList<travelBlogItem>>()
    var data1 = data.value
    fun liveobser(lifecycleOwner: LifecycleOwner):MutableList<travelBlogItem>?{
        /*var Dataobser = mutableListOf<travelBlogItem>()
        data.observe(lifecycleOwner, Observer {
            Dataobser =it

        })*/
        return data1
    }
}