package com.example.tripplnr.navigationscreens.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.Repository.TripRepository
import com.example.tripplnr.navigationscreens.Search.viewModel.MyViewModel
import com.example.tripplnr.navigationscreens.favorateFragment.ViewModel.FavorateViewModel

class ViewModelFactory(private val mData: TripRepository?=null, private var fData: MutableLiveData<MutableList<travelBlogItem>>?):ViewModelProvider.Factory {

//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//
//        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
//
//            return mData?.let { HomeViewModel(it) } as T
////            return MyViewModel(myParameter) as T
//        }
//
////        if (modelClass.isAssignableFrom(FavorateViewModel::class.java)){
////
////            return fData?.let { FavorateViewModel(it) } as T
//////            return MyViewModel(myParameter) as T
////        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }

}