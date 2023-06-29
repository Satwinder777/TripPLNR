package com.example.tripplnr.navigationscreens.ViewModel

import android.content.Context
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

    private var state:MutableLiveData<Boolean> = MutableLiveData(true)
    val m_state: LiveData<Boolean> get() = state

    fun update_state(){
        state.value = false
    }
//    fun init_state(){
//        state.value = true
//    }


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
            hotelchild(R.drawable.blog1,"Taj Hotel","Amritsar","3.3"),
            hotelchild(R.drawable.blog2,"The Bill Gates","America,Us","4.5"),
            hotelchild(R.drawable.blog3,"Punjab Hotel","Amritsar,Punjab","5.9"),
            hotelchild(R.drawable.blog4,"Chandighar Hotel","Chandighar, India","4.7"),
            hotelchild(R.drawable.blog5,"Us Hotel","Us,Amercia","4.8"),
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
                R.drawable.blog3,"the Golden Temple","12 may 23 ","1.32s",
                "The Golden Temple, also known as Sri Harmandir Sahib or Darbar Sahib, is a prominent Sikh gurdwara (place of worship) located in Amritsar, Punjab, India. It is one of the most important and spiritually significant pilgrimage sites for Sikhs worldwide.", isfavorate = true),

            travelBlogItem(
                R.drawable.blog2,"the Royal Temple","12 may 23 ","1.35s",
                "Wat Phra Kaew (Temple of the Emerald Buddha) - Located within the Grand Palace complex in Bangkok, Thailand, this temple is considered one of the most sacred sites in Thai Buddhism and is closely associated with the Thai royal family.", isfavorate = false),
            travelBlogItem(
                R.drawable.blog1,"the Swanrana mandhir ","12 may 23 ","1.11s",
                " \"Swarana Mandhir\" or \"Swarna Mandir\" in relation to a royal temple. It's possible that the term may refer to a temple with a golden or gilded appearance, as \"Swarana\" or \"Swarna\" means \"golden\" in certain languages.", isfavorate = true),
            travelBlogItem(
                R.drawable.blog4,"the love city","12 may 23 ","12.32s",
                "The term \"Love City\" is a general term that can refer to different cities around the world known for their association with love, romance, or a romantic atmosphere. There are several cities that are often referred to as \"Love Cities\" due to their romantic ambiance, scenic beauty, or historical connections to love and romance.", isfavorate = false),
            travelBlogItem(
                R.drawable.blog4,"the Punjaab","12 may 23 ","59.32s",
                "Punjab is a region in South Asia that is divided between two countries: India and Pakistan. It is a culturally rich and historically significant region known for its vibrant traditions, agricultural prosperity, and religious diversity.", isfavorate = true),

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


