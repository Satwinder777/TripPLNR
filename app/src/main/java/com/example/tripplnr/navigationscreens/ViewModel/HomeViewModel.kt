package com.example.tripplnr.navigationscreens.ViewModel

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.LiveDataVM.Live.data
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    interface MyRepository {
        fun getData(): MutableList<travelBlogItem>
    }
   val rc2: MutableLiveData<MutableList<hotelTitle>> = MutableLiveData()


     val itemList: MutableLiveData<MutableList<travelBlogItem>> = MutableLiveData( )

//    fun setNotfav(item: travelBlogItem){
//        itemList.value?.forEach {
//            if (it == item){
//                it.isfavorate = false
//            }
//        }
//    }
//    fun setfav(item: travelBlogItem){
//        itemList.value?.forEach {
//            if (it == item){
//                it.isfavorate = true
//            }
//        }
//    }



    // seePreviewTemplate
//    private val seePreviewTemplate = MutableLiveData<BaseCallbackState>()
//    val liveDataSeePreviewTemplate: LiveData<BaseCallbackState> get() = seePreviewTemplate



   lateinit var lifecycleowner :LifecycleOwner








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
fun initData(){
    itemList.value = data11()
}
     fun data11(): MutableList<travelBlogItem> {
        val  list  = mutableListOf<travelBlogItem>(
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




    fun addItemToFavorites(item: travelBlogItem) {
        val favorites = itemList.value ?: mutableListOf()
        favorites.add(item)
        itemList.value = favorites
    }

    fun removeItemFromFavorites(item: travelBlogItem, position: Int?) {
        val favorites = itemList.value ?: return
//        if (favoriteItems.value?.contains(item) == true){
//
//            val index=favoriteItems.value!!.indexOf(item)
//            favorites[index].isfavorate = false
//
//        }
//        else{
//            Log.e("test34", "removeItemFromFavorites: ${itemList.value}   esle", )
//        }
        itemList.value = favorites
        Log.e("test34", "removeItemFromFavorites: ${itemList.value}", )
    }


//    private fun loadData() {
//        viewModelScope.launch {
//            favoriteItems.value = repository.getData()
//        }
//    }


//    fun getItemList(): MutableLiveData<MutableList<travelBlogItem>> {
//        return itemList
//    }
//    fun additem(item :travelBlogItem){
//        itemList.value?.add(item)
//    }
//    fun removeUpdate(index: Int,lifecycleOwner: LifecycleOwner){
//
//        getItemList().observe(lifecycleOwner, Observer {
//            it[index].isfavorate = false
////            it.removeAt(index)
//            itemList.value = it
//        })
//    }

//    fun filteredData() : MutableList<travelBlogItem> {
//
//        var filterDAta = mutableListOf<travelBlogItem>()
//        if (getItemList().value!=null){
//
//          filterDAta = getItemList().value?.filter {
//              it.isfavorate==true
//
//          }?.toMutableList()!!
//
//
//
//        }
//
//         return filterDAta
//
//    }

    //

}


