package com.example.tripplnr.navigationscreens.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tripplnr.navigationscreens.Repository.TripRepository

class ViewModelFactory(private val mData: TripRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MyViewModel::class.java)){

            return MyViewModel(mData) as T
//            return MyViewModel(myParameter) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}