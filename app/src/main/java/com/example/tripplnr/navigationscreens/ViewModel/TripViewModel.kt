package com.example.tripplnr.navigationscreens.ViewModel

import android.util.Log
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.Repository.TripRepository

//class TripViewModel(private val repository: TripRepository):BaseViewModel() {
//
//
//    fun fetchData() {
//        // Use the repository to fetch data
//        val data = repository.getData()
//        // Do something with the fetched data
//        Log.e("Ttest29", "fetchData: ", )
//
//    }
//
//    fun saveData(data: Massage) {
//        // Use the repository to save data
//        repository.saveData(data)
//        Log.e("Ttest29", "saveData: ", )
//    }
//}

class MyViewModel(private val repository: TripRepository) : ViewModel() {




//    fun tas1(){
//       repository.getData()
//        Log.e("test295", "tas1: task1call In MyViewModel getting data", )
//    }
//
//    fun tas2(){
//    repository.saveData()
//        Log.e("test295", "tas2: task1call In MyViewModel saving data", )
//
//    }

//    private val myLiveData1 = MutableLiveData<List<travelBlogItem>>()
//    val myLiveData: LiveData<travelBlogItem> = myLiveData1
//var list =        mutableListOf<travelBlogItem>(travelBlogItem(R.drawable.exploreimg,"the Hira Factory","12 may 23","12mint","the shergill production"))
//
//    private val liveDataList2 = MediatorLiveData<MutableList<travelBlogItem>>()
//    val liveDataList: LiveData<MutableList<travelBlogItem>> = liveDataList2

    // Update the LiveData value
//    fun updateData(newValue: MutableList<travelBlogItem>) {
//        liveDataList2.value = newValue
//    }

//    fun updateRecyclerViewData(newData: MutableList<travelBlogItem>):List<travelBlogItem> {
//        recyclerViewData.value = newData
//
//        return  newData
//    }
//    fun addd(item : travelBlogItem){
//        recyclerViewData.value?.add(item)
//    }
//    fun remove(item: travelBlogItem){
//
//        recyclerViewData.value?.forEachIndexed { index, travelBlogItem ->
//            var b = item.aboutText
//
//            if (travelBlogItem.aboutText==b){
//                recyclerViewData.value?.removeAt(index)
//            }
//        }
//    }



    private val itemList: MutableLiveData<MutableList<travelBlogItem>> = MutableLiveData()
    private val rc2: MutableLiveData<MutableList<hotelTitle>> = MutableLiveData()


    // Method to fetch and update the list of items

    fun datahandle(){
        var list  = mutableListOf<travelBlogItem>(travelBlogItem(R.drawable.explore2,"the Golden Temple","12 may 23 ","1.32s",R.string.testLine.toString()),

            travelBlogItem(R.drawable.exploreimg,"the Royal Temple","12 may 23 ","1.35s",R.string.testLine.toString()),
            travelBlogItem(R.drawable.exploreimg,"the Swanrana mandhir ","12 may 23 ","1.11s",R.string.testLine.toString()),
            travelBlogItem(R.drawable.explore2,"the love city","12 may 23 ","12.32s",R.string.testLine.toString()),
            travelBlogItem(R.drawable.exploreimg,"the Punjaab","12 may 23 ","59.32s",R.string.testLine.toString()),

            )
        itemList.value = list

    }

    fun getItemList(): MutableLiveData<MutableList<travelBlogItem>> {
        return itemList
    }
    fun additem(item :travelBlogItem){
        itemList.value?.add(item)
    }
    fun remove(index: Int){
        itemList.value?.removeAt(index)
    }


   fun hotelData(){
        val hotelchildData  = mutableListOf<hotelchild>(
            hotelchild(R.drawable.explore2,"Taj Hotel","Amritsar","3.3"),
            hotelchild(R.drawable.exploreimg,"The Bill Gates","America,Us","4.5"),
            hotelchild(R.drawable.explore2,"Punjab Hotel","Amritsar,Punjab","5.9"),
            hotelchild(R.drawable.exploreimg,"Chandighar Hotel","Chandighar, India","4.7"),
            hotelchild(R.drawable.explore2,"Us Hotel","Us,Amercia","4.8"),
        )
        var list  = mutableListOf<hotelTitle>(
            hotelTitle("Top Hotel",hotelchildData),
            hotelTitle("Best Hotel",hotelchildData),
            hotelTitle("Old Hotel",hotelchildData),
            hotelTitle("Gold Hotel",hotelchildData),


            )
        rc2.value = list

    }
    fun rc2List(): MutableLiveData<MutableList<hotelTitle>> {
        return rc2
    }



}


