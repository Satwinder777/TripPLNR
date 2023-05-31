package com.example.tripplnr.navigationscreens.Search.hotel

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TimeUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHotelsBinding
import com.example.tripplnr.navigationscreens.Constants.AllConstants
import com.example.tripplnr.navigationscreens.DataCls.daterangedataCls
import com.example.tripplnr.navigationscreens.DataCls.guestdatacls
import com.example.tripplnr.navigationscreens.Home.adapter.TravelBlogAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Search.adapter.RecyclerAdapterSearchFr
import com.example.tripplnr.navigationscreens.hotelListFragment.HotelListFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.LocalDate
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
import java.util.concurrent.TimeUnit


class HotelsFragment : Fragment(){
    private lateinit var binding: FragmentHotelsBinding
    private lateinit var rc: RecyclerView
    var roomc:Int = 0
    var adultc:Int = 0
    var childc:Int = 0
    private val guestLiveData =  MutableLiveData<guestdatacls>()
    private val dateLiveData =  MutableLiveData<String>()
    private lateinit var placesClient: PlacesClient




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHotelsBinding.inflate(layoutInflater)
        val Guest = "$adultc Guests, $roomc Rooms"
        checklivedata(guestLiveData,dateLiveData)

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        rc = binding.searchFrRecycler1
//        GlobalScope.launch {

        initView()
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
        search.setOnClickListener {
//            if ( it is SearchView){
//                it.setBackground()
//                it.setPadding(0, 0, 0, 0)
//            }


            addAddress()
            if (binding.searchView.query.isNullOrEmpty().not() && guestLiveData.value!=null && dateLiveData.value!=null ){
                val newFragment = HotelListFragment()

                checklivedata(guestLiveData,dateLiveData)
                val args = Bundle()
                args.putParcelable("guest", guestLiveData.value)
                args.putString("date",dateLiveData.value)
                args.putString("query",binding.searchView.query.toString())
                newFragment.arguments = args

//                Log.e("test311", "onViewCreated: ${binding.searchView.query.toString()}", )
//            val targetFragment = TargetFragment()
                val fragmentManager = requireParentFragment().parentFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            else{
                Toast.makeText(requireContext(), "Please search query", Toast.LENGTH_SHORT).show()
            }

        }
//        GlobalScope.launch {
//            var hotel = binding.hotelChip
//            var blog = binding.blogChip
//            changeScreen(hotel)
//            changeScreen(blog)
//        }

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
//                    var bottomSelectDate = BottomSheetDialog(requireContext())
//                    var view1 = layoutInflater.inflate(R.layout.bottomsheet_selectdate, null, false)
//                    bottomSelectDate.setContentView(view1)
//                    bottomSelectDate.show()
//                    var closebtn = view1.findViewById<MaterialCardView>(R.id.close_selectDate)
//                    closebtn.setOnClickListener {
//                        bottomSelectDate.dismiss()
//                    }
//
//                    var calendarView = view1.findViewById<CalendarView>(R.id.calendarView)
//                    calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
//                        // Handle the selected date
//                        // year: Int - Selected year
//                        // month: Int - Selected month (0-based, i.e., January is 0)
//                        // dayOfMonth: Int - Selected day of the month
//
//                        dayOfMonth0 = dayOfMonth
//                        month0 = month + 1
//                        year0 = year
//
//                    }
//
//                    var selectBtn = view1.findViewById<MaterialButton>(R.id.selectCalenderData)
//                    selectBtn.setOnClickListener {
//                        Toast.makeText(
//                            requireContext(),
//                            "the day :$dayOfMonth0, $month0 , $year0",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        bottomSelectDate.dismiss()
//                    }

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
//
//                    var v1 = layoutInflater.inflate(R.layout.bottomsheet_selectdate, null, false)
//                    var dp = v1.findViewById<DatePicker>(R.id.datepicker) as DatePicker





//                   var datep =  v1.findViewById<DatePicker>(R.id.datepicker)
//                   var max =  datep.maxDate
//                   var min =  datep.maxDate
////                    var list = mutableListOf<datepick>()
//                    var count=0
//
//                    datep.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
//                        var list = mutableListOf<datepick>()
//                        var curentdate =   datepick(dayOfMonth,monthOfYear)
//                        list.add(curentdate)
//                        Log.e("listdate", "onDateChanged: $list", )
//                        Toast.makeText(requireContext(), "changew" +
//                                "", Toast.LENGTH_SHORT).show()
//                    }




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
            Log.e("livedata", " ${guestLiveData.value} ", )
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

fun checklivedata(m1:MutableLiveData<guestdatacls>?,m2:MutableLiveData<String>?){

    var TAG = "test31"
    if (m1?.value != null && m2?.value != null){

        Log.e(TAG, "checklivedata: !null", )
    }
    else{
        guestLiveData.value = guestdatacls(1,1)
        dateLiveData.value = "Sun, 8 jun - Sun, 8 Jun "
//        binding.GuestTextView.setText("1 Guest,1 Room")
//        binding.rangeDAteTextView.setText("8 jun - 8 Jun")
        Log.e(TAG, "checklivedata: null ${guestLiveData.value},${dateLiveData.value}  ", )
    }
}
    private fun addAddress() {

try {
    val fields = listOf(Place.Field.ID, Place.Field.NAME)

    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
        .build(requireActivity())
    startActivityForResult(intent, AllConstants.REQUEST_CODE.AUTOCOMPLETE_REQUEST_CODE)

    Log.e("field12", "addAddress: ${      fields[1].name  }", )
}
catch (e:Exception){
    Log.e("exp", "addAddress: ${e.message}", )
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


