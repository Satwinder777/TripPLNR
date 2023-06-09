package com.example.tripplnr.navigationscreens.hotelListFragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHotelListBinding
import com.example.tripplnr.navigationscreens.DataCls.MyDataHandle
import com.example.tripplnr.navigationscreens.DataCls.my_latlng
import com.example.tripplnr.navigationscreens.DataCls.sortData
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelListClass
import com.example.tripplnr.navigationscreens.Home.hotel.HotelFragment
import com.example.tripplnr.navigationscreens.Search.hotel.activity.FilterBottomSheet
import com.example.tripplnr.navigationscreens.Search.hotel.activity.HotelList2Activity
import com.example.tripplnr.navigationscreens.hotelListFragment.adapter.Hotel_list_recyclerAdapter
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.model.PlaceType
import com.google.maps.model.PlacesSearchResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HotelListFragment : Fragment(), Hotel_list_recyclerAdapter.viewdetail {
    private lateinit var binding: FragmentHotelListBinding
    private lateinit var rc: RecyclerView
    private lateinit var query :String
    val data = mutableListOf<String>()
    var sortingTech = 0
    var final_sort_technique = 0
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var adapter : Hotel_list_recyclerAdapter
    private lateinit var editor : SharedPreferences.Editor
    private lateinit var g_latLng: LatLng
    private lateinit var f_latLng: com.google.maps.model.LatLng

    var p_list = ArrayList< PlacesSearchResult>()
    var arr =  ArrayList<MyDataHandle>()


//    private lateinit var selected_m_view : TextView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHotelListBinding.inflate(layoutInflater)

        sharedPreferences = requireContext().getSharedPreferences("sorting the data",Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val data = Allfun.guestLiveData.value
        binding.roomtextviewHotelList.setText("${data?.rooms}")
        binding.guestTexthotelList.setText("${ data?.guest }")

         query = arguments?.getString("query","default" ) ?: ""

        binding.dateTextviewHotelList.setText(Allfun.dateLiveData.value)
        binding.queryTextViewHotelList.setText(query)
       var default_sort =  sharedPreferences.getInt("sorting_type",0)
        final_sort_technique = default_sort


//        Log.e("sattadatta", "onCreateView: $data_m", )
//        view_sorting.text.setTextColor(ContextCompat.getColor(requireContext(),R.color.yellow))
//        item.image.visibility = View.VISIBLE
//        sortingTech = item.text.tag.toString()

        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var view_sorting =  requireView().findViewWithTag<ConstraintLayout>(sorting_tech)
        rc = binding.hotelListFragmentRecyclerView

        getPlaceRelatedLatLng(query)
        if (arr.isNullOrEmpty()){
//            Toast.makeText(requireContext(), "nullnempty", Toast.LENGTH_SHORT).show()
            arr.add(MyDataHandle(p_list))
        }
        else{
            Toast.makeText(requireContext(), "data found", Toast.LENGTH_SHORT).show()
            arr.toList().forEach {
                Log.e("TAG", "testpart: ${it.data}", )
            }

        }


//        view_sorting?.forEachIndexed { index, view0 ->
//            when(view0){
//                is ImageView->{
//                    view0.visibility = View.VISIBLE
//                }
//                is TextView->{
//                    view0.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
//                }
//                else->{
//                    Toast.makeText(requireContext(), "not get require view!!", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }



//            adapter.notifyDataSetChanged()


        var backBtn = binding.backHotelList
        backBtn.setOnClickListener {
            //pop back
            fragmentManager?.popBackStack()
//            Log.e("test12", "onViewCreated: ${fragmentManager?.backStackEntryCount} ")

        }
        binding.mapChip.setOnClickListener {
            val intent = Intent(requireContext(),HotelList2Activity::class.java)
            intent.putExtra("query",query)
            intent.putExtra("latLngKey",my_latlng(g_latLng))



            intent.putParcelableArrayListExtra("PlaceSearchResult",arr)


            startActivity(intent)
        }


        Log.d("checkAarr12", "onViewCreated: sjsdufh ", )

        doTask(binding.filterList)
        doTask(binding.sortList)
        binding.editDAtaIV.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        testpart()
    }
    private fun postData():List<hotelListClass>{
        var list = listOf<hotelListClass>(
            hotelListClass(R.drawable.exploreimg,"Taj Hotel","Punjab ,India",5f),
            hotelListClass(R.drawable.exploreimg,"The Royal Villa Hotel","Himachal ,India",4f),
            hotelListClass(R.drawable.exploreimg,"The Gold Hotel","Pathankot ,India",3f),
            hotelListClass(R.drawable.exploreimg,"Punjab Hotel","Pakistan ,India",4f),
        )
        return list

    }

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation", "InflateParams")
    fun doTask(view: View){
        var filter = binding.filterList
        var sort = binding.sortList
        view.setOnClickListener {
        val bottom = BottomSheetDialog(requireContext())
            when(view){
                filter-> {

//                    Allfun.openFragment(FilterBottomSheet(),requireParentFragment().parentFragmentManager)
                    val bottomSheetFragment = FilterBottomSheet()
                    bottomSheetFragment.show(requireParentFragment().parentFragmentManager, bottomSheetFragment.tag)
                    bottomSheetFragment.isCancelable = false
                }
                sort->{

                    val data_m = sharedPreferences.getString("index","0")
                    Log.e("testdata", "doTask: $data_m", )

                    var view = layoutInflater.inflate(R.layout.sort_dialog,null,false)
//                    selected_m_view = view.requireViewById(m)

                    bottom.setContentView(view)
                    bottom.show()

                    var closebtn =  view.findViewById<ImageButton>(R.id.closeSort)
                    closebtn.setOnClickListener{
                        bottom.dismiss()
                    }



                    var recommended = bottom.findViewById<TextView>(R.id.recommended)
                    var imgpos1 = bottom.findViewById<ImageView>(R.id.imgpos1)

                    var prizeLtoH = bottom.findViewById<TextView>(R.id.prizeLowtoHigh)
                    var imgpos2 = bottom.findViewById<ImageView>(R.id.imgpos2)

                    var prizeHtoL = bottom.findViewById<TextView>(R.id.prizeHightoLow)
                    var imgpos3 = bottom.findViewById<ImageView>(R.id.imgpos3)

                    var reviews = bottom.findViewById<TextView>(R.id.sortByReviews)
                    var imgpos4 = bottom.findViewById<ImageView>(R.id.imgpos4)

                    var starRatingHtoL = bottom.findViewById<TextView>(R.id.starRatingHighToLow)
                    var imgpos5 = bottom.findViewById<ImageView>(R.id.imgpos5)

                    var distanceNtoF = bottom.findViewById<TextView>(R.id.distanceNearToFar)
                    var imgpos6 = bottom.findViewById<ImageView>(R.id.imgpos6)

                    val applyBtn = bottom.findViewById<MaterialButton>(R.id.sortApplyBtn)
                    applyBtn?.setOnClickListener{
//                        Toast.makeText(requireContext(), sortingTech, Toast.LENGTH_SHORT).show()
                        editor.putInt("sorting_type",sortingTech)
                        editor.apply()
                        final_sort_technique = sortingTech


                        bottom.dismiss()
                    }




                    val listSortType = listOf(sortData(recommended!!,imgpos1!!),sortData(prizeLtoH!!,imgpos2!!),sortData(prizeHtoL!!,imgpos3!!)
                        ,sortData(reviews!!,imgpos4!!),sortData(starRatingHtoL!!,imgpos5!!),sortData(distanceNtoF!!,imgpos6!!),
                    )
//                    data_m?.toInt()?.let { it1 -> listSortType.get(it1) }
                    m_selected_item(final_sort_technique,listSortType)
                    mSortData(listSortType as List<sortData>)


                bottom.setCancelable(false)
                }
            }
        }
    }
    private fun m_selected_item(item:Int, list: List<sortData>){
        list.forEachIndexed { index, sortData ->
            if (index==item==true){
                sortData.text.setTextColor(ContextCompat.getColor(requireContext(),R.color.yellow))
                sortData.image.visibility = View.VISIBLE
            }else{
                sortData.text.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                sortData.image.visibility = View.GONE

            }


        }
    }
    @SuppressLint("ResourceAsColor", "ResourceType")
    private fun dotask(list: List<View>) {

        for (button in list) {
            button.setOnClickListener {
                // Reset background for all buttons
                for (b in list) {
                    data.remove(b.tag)

                    b.setBackgroundResource(R.drawable.bg_normal)

                    b.backgroundTintMode = PorterDuff.Mode.SRC_ATOP
                    val color = ContextCompat.getColor(requireContext(), R.color.brown)

                    b.backgroundTintList = ColorStateList.valueOf(color)

                    when(b){
                        is TextView->{

                            b.setTextColor(resources.getColor(R.color.black))
                        }
                        is LinearLayout->{
                            b.forEach {
                            if(it is ImageView){
                                it.setImageResource(R.drawable.star_ic)
                            }
                        }}
                        else->{
                            Toast.makeText(requireContext(), "error occured!!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if (b is MaterialButton){
                        b.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.yellow)

                    }

                }

                // Change background of the clicked button
                button.setBackgroundResource(R.drawable.bg_normal2)
                data.add(it.tag.toString())


                when(button){


                    is TextView->{
                        button.setTextColor(resources.getColor(R.color.white))
                    }
                    is LinearLayout->{
                        button.forEach {

                            when(it){
                                is ImageView->{
//                                    it.imageTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
                                    it.setImageResource(R.drawable.star_white)
                                }
                                else->{
                                    Toast.makeText(requireContext(), "eroor", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }}
                    else->{
                        Toast.makeText(requireContext(), "error occured!!", Toast.LENGTH_SHORT).show()
                    }
                }
                val color = ContextCompat.getColor(requireContext(), R.color.yellow)

                button.backgroundTintList = ColorStateList.valueOf(color)
                if (button is MaterialButton){
                    button.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.white)
                }



                when (button) {
                    list.get(0) -> {
                        // Actions for button1
//                        it.setBackgroundResource(R.drawable.card_shape)
                    }
                    list.get(1) -> {
                        // Actions for button2
//                        it.setBackgroundResource(R.drawable.card_shape)

                    }
                    list.get(2) -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)

                    }
                    list.get(3) -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)

                    }
                    list.get(4) -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)

                    }list.get(5) -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)

                    }
                    else->{
                        Log.e("errorblk", "dotask: error_block", )
                    }

                }
            }
        }

    }
    private fun mSortData(list: List<sortData>){
        for (item in list){

            item.text.setOnClickListener {
                // Reset background for all buttons
                for (mItem in list){
                    mItem.image.visibility = View.INVISIBLE
                    mItem.text.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                item.text.setTextColor(ContextCompat.getColor(requireContext(),R.color.yellow))
                item.image.visibility = View.VISIBLE
                sortingTech = list.indexOf(item)
                editor.putString("index",sortingTech.toString())
                editor.apply()
                Log.e("testdassa", "mSortData: $sortingTech", )

            }

        }

    }
    private fun onClickResetBtn(textView:TextView,list: List<View>){

        when(textView.id){
            R.id.rangeReset->{ setNormalbg(list) }
            R.id.parkingReset->{  setNormalbg(list) }
            R.id.paymentReset->{ setNormalbg(list)}
            R.id.propertyReset->{ setNormalbg(list)}
            R.id.hotelclassReset->{ setNormalbg(list)}
            R.id.customerreviewReset->{ setNormalbg(list)}
            R.id. amenitiesReset->{ setNormalbg(list)}
        }

    }
    @SuppressLint("ResourceType")
    private fun setNormalbg(list: List<View>) {
        for (item in list) {
            item.setBackgroundResource(R.drawable.bg_normal)

            item.backgroundTintMode = PorterDuff.Mode.MULTIPLY
            val color = ContextCompat.getColor(requireContext(), R.color.white)
//                materialButton.backgroundTintList = ColorStateList.valueOf(color)
            item.backgroundTintList = ColorStateList.valueOf(color)


            when(item){
                is TextView->{
                    item.setTextColor(resources.getColor(R.color.black))
                }
                is LinearLayout->{
                    item.forEach {
                        if (it is ImageView){
                            it.setImageResource(R.drawable.star_ic)
                        }
                    }
                }

                else->{
                    Toast.makeText(requireContext(), "error occured!!", Toast.LENGTH_SHORT).show()
                }

            }
            if (item is MaterialButton){
                item.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.yellow)
            }

        }
    }

    override fun checkDetails(view: View, position: Int) {
        Allfun.openWeb(requireContext())

    }

    override fun onCardClicks(
        view: View,
        position: Int,
        placetxt: Pair<String, String>,
        rating: Float
    ) {
        try {
            val bundle = Bundle()
//                args.putParcelable("guest", guestLiveData.value)
//                args.putString("date",dateLiveData.value)



            bundle.putString("place", placetxt.first)
            bundle.putString("city", placetxt.second)
            bundle.putFloat("rate", rating)
            bundle.putParcelableArrayList("PlacesSearchResult", arr )



//
            val newFragment = HotelFragment()
//       var HotelFragment_id =  newFragment.id
            newFragment.arguments = bundle
            val fragmentManager = requireParentFragment().parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, newFragment)
            transaction.addToBackStack("hotel_fragment")
            transaction.commit()
        }
        catch (e:Exception){
            Log.e("error_occurs", "onCardClicks: error is := ${e.message}", )
        }

//        findNavController().navigate(HotelFragment_id,bundle)
    }




    @SuppressLint("LongLogTag")
    fun getPlaceRelatedLatLng(searchquery:String){

        val apiKey = getString(R.string.google_maps_key3)
        var addressList: List<Address>? = null
        val geoApiContext = GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()

        if(searchquery!=null || searchquery !=""){
            try {
                val geocoder = Geocoder(requireContext())
                addressList = geocoder.getFromLocationName(searchquery,1)
            }
            catch (e:Exception){
                Log.e("hotelListfragment_exception", "getPlaceRelatedLatLng: ${e.message}", )
            }

        }else{

            Log.e("null quesy", "getPlaceRelatedLatLng: query nulll", )
        }
        val address = addressList!![0]
         g_latLng = LatLng(address.latitude,address.longitude)
         f_latLng = com.google.maps.model.LatLng(address.latitude,address.longitude)
        val radius = getString(R.string.nearby_search_radius)
        Log.e("lat_lng", "onCreate: $g_latLng,$f_latLng", )


//        ,PlaceType.BAR
        val response =  PlacesApi.nearbySearchQuery(geoApiContext,f_latLng).location(f_latLng).type(PlaceType.LODGING).radius(radius.toInt()).await()
        if (response.results!=null){
            var final_list = response.results.distinctBy { it.geometry.location }
            final_list.forEach {
                p_list.add(it)
            }
            val gms_latlng = LatLng(f_latLng.lat,f_latLng.lng)
         adapter  = Hotel_list_recyclerAdapter(emptyList(),this@HotelListFragment,gms_latlng)
            adapter.list = final_list
            rc.adapter = adapter

//            fjsk
        }else{
            Log.e("responce_data", "getPlaceRelatedLatLng: check responce code hotel_list_fragmnet ", )
        }
    }
    fun testpart(){
        try {

        }
        catch (e:Exception){
            Log.e("checkAarr1", "testpart: ${e.message}", )
        }

    }


}