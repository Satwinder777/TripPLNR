package com.example.tripplnr.navigationscreens.Search.hotel

import android.annotation.SuppressLint
import android.content.Context
import android.content.SearchRecentSuggestionsProvider
import android.database.Cursor
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.cursoradapter.widget.CursorAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHotelsBinding
import com.example.tripplnr.navigationscreens.Constants.AllConstants
import com.example.tripplnr.navigationscreens.DataCls.guestdatacls
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Search.adapter.RecyclerAdapterSearchFr
import com.example.tripplnr.navigationscreens.hotelListFragment.HotelListFragment
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.example.tripplnr.navigationscreens.objectfun.Allfun.dateLiveData
import com.example.tripplnr.navigationscreens.objectfun.Allfun.guestLiveData
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class HotelsFragment : Fragment(){
    private lateinit var binding: FragmentHotelsBinding
    private lateinit var rc: RecyclerView
    var roomc:Int = 1
    var adultc:Int = 1
    var childc:Int = 1

    private lateinit var placesClient: PlacesClient
//    private lateinit var suggestionAdapter: SuggestionAdapter
//    private lateinit var searchView :EditText




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHotelsBinding.inflate(layoutInflater)

        val Guest = "$adultc Guests, $roomc Rooms"
        Allfun

        checklivedata(guestLiveData,dateLiveData)
        Log.e("testdata", "onCreateView:$guestLiveData,$dateLiveData ", )

//        searchView = binding.searchView
        binding.GuestTextView.setText(Guest)
//        binding.GuestTextView.setText(guestLiveData.value?.guest)
        binding.rangeDAteTextView.setText(dateLiveData.value.toString())
//        val recyclerView = view?.findViewById<RecyclerView>(R.id.searchFrRecycler1)
//        var myAdapter = RecyclerAdapterSearchFr(hotelData())
//        recyclerView?.adapter = myAdapter
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged", "InflateParams")
    companion object {
        const val AUTHORITY = "com.example.yourapp.YourSearchSuggestionProvider"
        const val MODE = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        rc = binding.searchFrRecycler1
//        GlobalScope.launch {

//        initView()

        rc = binding.searchFrRecycler1
        rc.layoutManager = LinearLayoutManager(requireContext())

        val adapter = RecyclerAdapterSearchFr(hotelData())
        rc.adapter = adapter
        adapter.notifyDataSetChanged()
//        }

        GlobalScope.launch {
            bottomTask(binding.linearDate)
            bottomTask(binding.linearGuest)

//            var bottomSelectGuest = BottomSheetDialog(requireContext())
//            var view1 = layoutInflater.inflate(R.layout.bottom_sheet_gestlist,null,false)
//            bottomSelectGuest.setContentView(view1)
//            var item1 = bottomSelectGuest.findViewById<ImageView>(R.id.roomaddbtn)
//            item1?.setOnClickListener {
//                guestDataHandler(item1)
//                Log.e("test24", "bottomTask: clicked", )
//            }
        }
        val search = binding.searhhotel


       var autocm =  binding.autocm
        autocm.apply {
            threshold = 0

            val suggestAdapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.cities) )
            setAdapter(suggestAdapter)
            this.setOnClickListener{
                showDropDown()
            }
            //  performCompletion()
        }



        search.setOnClickListener {

            val searchView = autocm
            if (searchView.text.isNullOrEmpty().not() && guestLiveData.value!=null && dateLiveData.value!=null ){
                val newFragment = HotelListFragment()

                val args = Bundle()
//                args.putParcelable("guest", guestLiveData.value)
//                args.putString("date",dateLiveData.value)
                args.putString("query",searchView.text.toString())
                newFragment.arguments = args

//                Log.e("test311", "onViewCreated: ${binding.searchView.query.toString()}", )
//            val targetFragment = TargetFragment()
                val fragmentManager = requireParentFragment().parentFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, newFragment)
                transaction.addToBackStack(null)
                transaction.setReorderingAllowed(true)
                transaction.commit()
            }
            else{
                Toast.makeText(requireContext(), "Please search query", Toast.LENGTH_SHORT).show()
            }

        }


    }

    fun hotelData(): MutableList<hotelTitle> {
        val hotelchildData = mutableListOf<hotelchild>(
            hotelchild(R.drawable.explore2, "Taj Hotel", "Amritsar", "3.3"),
            hotelchild(R.drawable.exploreimg, "The Bill Gates", "America,Us", "4.5"),
            hotelchild(R.drawable.explore2, "Punjab Hotel", "Amritsar,Punjab", "5.9"),
            hotelchild(R.drawable.exploreimg, "Chandighar Hotel", "Chandighar, India", "4.7"),
            hotelchild(R.drawable.explore2, "Us Hotel", "Us,Amercia", "4.8"),
        )
        var list = mutableListOf<hotelTitle>(
            hotelTitle("Top Hotel", hotelchildData),
            hotelTitle("Best Hotel", hotelchildData),
            hotelTitle("Old Hotel", hotelchildData),
            hotelTitle("Gold Hotel", hotelchildData),

            )
        return list
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("InflateParams", "MissingInflatedId", "SimpleDateFormat")
    private fun bottomTask(view: View) {

        var selectdate = binding.linearDate
        var selectGuest = binding.linearGuest

        var dayOfMonth0 = 0
        var month0 = 0
        var year0 = 0
        view.setOnClickListener {
            when (view) {
                selectdate -> {


                    val datePicker = MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select Date Range")
                        .build()

                    datePicker.addOnPositiveButtonClickListener { selection ->
                        val startDate = selection.first
                        val endDate = selection.second


                        val date = Date(startDate)
                        val dateFormat = SimpleDateFormat("EEE ,dd MMM", Locale.getDefault())
                        var startdate0 = dateFormat.format(date)

                        val date2 = Date(endDate)
                        val dateFormat2 = SimpleDateFormat("EEE ,dd MMM", Locale.getDefault())

                        var enddate0 = dateFormat2.format(date2)
                        var rangestr = "$startdate0 - $enddate0"
                        binding.rangeDAteTextView.setText(rangestr)

                        dateLiveData.value = rangestr
                    }

                    datePicker.show(childFragmentManager, "DateRangePicker")

                }


                selectGuest -> {

                    var bottomSelectGuest = BottomSheetDialog(requireContext())
                    var view1 = layoutInflater.inflate(R.layout.bottom_sheet_gestlist, null, false)
                    bottomSelectGuest.setContentView(view1)
                    bottomSelectGuest.show()
                    var closebtn = view1.findViewById<MaterialCardView>(R.id.close_selectGuest)
                    closebtn.setOnClickListener {
                        bottomSelectGuest.dismiss()
                    }

                    guestDataHandler(bottomSelectGuest)

                }
                else -> {
                    Toast.makeText(requireContext(), "differentview", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    private fun guestDataHandler(view: BottomSheetDialog?) {

        var addroom = view?.findViewById<ImageView>(R.id.roomaddbtn)
        var addadult = view?.findViewById<ImageView>(R.id.adultaddbtn)
        var addchild = view?.findViewById<ImageView>(R.id.childrenaddbtn)

        var roomsub = view?.findViewById<ImageView>(R.id.roomsubbtn)
        var roomadult = view?.findViewById<ImageView>(R.id.adultsubbtn)
        var roomchild = view?.findViewById<ImageView>(R.id.childrensubbtn)

        var roomcount = view?.findViewById<TextView>(R.id.roomcount)
        var adultcount = view?.findViewById<TextView>(R.id.adultcount)
        var childcount = view?.findViewById<TextView>(R.id.childrencount)

        var a = roomdata(addroom,roomsub,roomcount )
        var b = adultdata(addadult,roomadult,adultcount )
        var c = childdata(addchild,roomchild,childcount )
       var submit =  view?.findViewById<MaterialButton>(R.id.guestApply)
        submit?.setOnClickListener{
//            Toast.makeText(requireContext(), "  Guest Are  R:$roomc,A:$adultc,C:$childc, ", Toast.LENGTH_SHORT).show()
            view?.dismiss()

            val Guest = "$adultc Guests, $roomc Rooms"
            binding.GuestTextView.setText(Guest)

            guestLiveData.value = guestdatacls(adultc,roomc)
            Log.e("livedata", " ${guestLiveData.value} ")
//            liveData

        }



    }

    private fun roomdata(addView: ImageView?,subView: ImageView?,textshowview:TextView?){
        var totalRoom = 0
        addView?.setOnClickListener {

            totalRoom += 1
            textshowview?.setText(totalRoom.toString())
            roomc = totalRoom

        }
        subView?.setOnClickListener {
            if (totalRoom==0){
                textshowview?.setText("0")
            }else{
                totalRoom -=1
                textshowview?.setText(totalRoom.toString())
            }
            roomc = totalRoom
        }


    }

    private fun adultdata(addView: ImageView?,subView: ImageView?,textshowview:TextView?) {

        var count1 = 0


        addView?.setOnClickListener {
            count1 += 1
            textshowview?.setText(count1.toString())
            adultc = count1
        }

        subView?.setOnClickListener {


            if (count1==0){
                textshowview?.setText("0")
            }else{
                count1-=1
                textshowview?.setText(count1.toString())
                adultc = count1
            }
        }

    }

    private fun childdata(addView: ImageView?,subView: ImageView?,textshowview:TextView?) {

        var count1 = 0


        addView?.setOnClickListener {
            count1 += 1
            textshowview?.setText(count1.toString())
            childc = count1
        }

        subView?.setOnClickListener {


            if (count1==0){
                textshowview?.setText("0")
            }else{
                count1-=1
                textshowview?.setText(count1.toString())
                childc = count1

            }
        }

    }

@SuppressLint("SetTextI18n")
fun checklivedata(m1:MutableLiveData<guestdatacls>?, m2:MutableLiveData<String>?){

    var TAG = "test31"
    if (m1?.value != null && m2?.value != null){

        var data = guestLiveData.value

        val adlt = data?.guest
        val room = data?.rooms
        binding.GuestTextView.setText("$adlt Guest,$room Rooms")
        binding.rangeDAteTextView.setText(dateLiveData.value)
        Log.e(TAG, "checklivedata: !null")

    }
    else{
        guestLiveData.value = guestdatacls(1,1)
        dateLiveData.value = "Sun, 8 jun - Sun, 8 Jun "
//        binding.GuestTextView.setText("1 Guest,1 Room")
//        binding.rangeDAteTextView.setText("8 jun - 8 Jun")
        Log.e(TAG, "checklivedata: null ${guestLiveData.value},${dateLiveData.value}  ")
    }
}
    @SuppressLint("ResourceType")
    private fun addAddress() {

try {
    val fields = listOf(Place.Field.ID, Place.Field.NAME)

    val intent1 = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
        .build(requireActivity())
    startActivityForResult(intent1, AllConstants.REQUEST_CODE.AUTOCOMPLETE_REQUEST_CODE)

    Log.e("field12", "addAddress: ${fields[1].name}")


//    val autocompleteFragment = fragmentManager?.findFragmentById(R.layout.fragment_hotel) as AutocompleteSupportFragment
//
//// Specify the types of place data to return.
//    autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

//    intent.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//        override fun onPlaceSelected(place: Place) {
//            // Handle the selected place.
//            val placeName = place.name
//            Log.e("test23", "onPlaceSelected: $placeName", )
//            // Do something with the place name.
//        }
//
//        override fun onError(status: Status) {
//            // Handle the error.
//            Log.e("test23", "onError: ${status.status}", )
//        }
//    })

}
catch (e:Exception){
    Log.e("exp", "addAddress: ${e.message}")
}


//        try {
//            if (marker.isVisible) {
//                marker.remove()
//                // If you have a marker list and want to remove a specific marker, uncomment the following lines:
//                // if (markerlist.isNotEmpty()) {
//                //     markerlist.remove(markerlist[0])
//                // }
//            }
//        } catch (e: Exception) {
//            Log.e("newsc", "setUpUi: ${e.message}")
        }
    private fun initView() {


        if (!Places.isInitialized()) {
            Places.initialize(
                requireActivity(), getString(R.string.google_maps_key), Locale.US
            )
        }
        placesClient = Places.createClient(requireActivity())

        }

    }

//}



