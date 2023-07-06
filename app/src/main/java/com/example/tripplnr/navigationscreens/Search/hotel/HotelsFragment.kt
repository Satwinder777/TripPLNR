package com.example.tripplnr.navigationscreens.Search.hotel

import android.annotation.SuppressLint
import android.content.Context
import android.content.SearchRecentSuggestionsProvider
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHotelsBinding
import com.example.tripplnr.navigationscreens.DataCls.guestdatacls
import com.example.tripplnr.navigationscreens.DataCls.namePlace
import com.example.tripplnr.navigationscreens.Home.dataclass.guest_Children
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Search.adapter.RecyclerAdapterSearchFr
import com.example.tripplnr.navigationscreens.Search.adapter.guest_Child_Adapter
import com.example.tripplnr.navigationscreens.hotelListFragment.HotelListFragment
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.example.tripplnr.navigationscreens.objectfun.Allfun.dateLiveData
import com.example.tripplnr.navigationscreens.objectfun.Allfun.guestLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.model.PlaceType
import com.google.maps.model.PriceLevel
import com.google.maps.model.RankBy
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class HotelsFragment : Fragment() {
    private lateinit var binding: FragmentHotelsBinding
    private lateinit var rc: RecyclerView
    var roomc: Int = 1
    var adultc: Int = 1
    var childc: Int = 1
    private lateinit var share_pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var adapter: guest_Child_Adapter
    var final_count :Int = 0

    var m_data_show = mutableListOf<String>()
    var list = mutableListOf<guest_Children>()
    private lateinit var textView: TextView
    private lateinit var spinner: Spinner


    private lateinit var placesClient: PlacesClient
//    private lateinit var suggestionAdapter: SuggestionAdapter
//    private lateinit var searchView :EditText


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHotelsBinding.inflate(layoutInflater)
        initView()
        share_pref =
            requireContext().getSharedPreferences("share_pref_hotel_fragment", Context.MODE_PRIVATE)
        editor = share_pref.edit()


//        var one = 1
//        var defaultset  = setOf(one.toString(),one.toString())


        val adult_count = share_pref.getInt("guest_count", 1)
        val room_count = share_pref.getInt("room_count", 1)
        val child_ct = share_pref.getInt("childrens", 0)
        childc = child_ct

        var data_date_picker = share_pref.getString("selected_date", Allfun.freshdate())

        binding.rangeDAteTextView.setText(data_date_picker)
//        var m_guest = data_guest?.forEachIndexed { index, s ->
//
//            var g:Int = 0
//            var r:Int = 0
//            when(index){
//                0->{ g = s.toInt()}
//                1->{ r = s.toInt() }
//                else->{
//                    Log.e("testcase", "onCreateView: wrong index", )}
//            }
        val Guest = "$adult_count Guests, $room_count Rooms"
        binding.GuestTextView.setText(Guest)
        Allfun.dateLiveData.value = data_date_picker
        Allfun.guestLiveData.value = guestdatacls(adult_count, room_count)


//        Allfun.guestLiveData.value = data_guest


//        binding.rangeDAteTextView.setText(dateLiveData.value.toString())
        textView = TextView(requireContext())
        spinner = Spinner(requireContext())

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
//        Log.e("isApiAuthorized", "onViewCreated: ${isApiAuthorized()}")

        getTopHotels()
        rc = binding.searchFrRecycler1
        rc.layoutManager = LinearLayoutManager(requireContext())

        val adapter = RecyclerAdapterSearchFr(hotelData())
        adapter.notifyDataSetChanged()

        rc.adapter = adapter
//        }

        GlobalScope.launch {
            bottomTask(binding.linearDate)
            bottomTask(binding.linearGuest)

        }
        val searhhotel = binding.searhhotelButton
        var autocm = binding.AutoCompleteTextView


//        autocm.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,placesClient)
        autocm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (Allfun.isInternetAvailable(requireContext()) == true.not()) {
                    autocm.setError("check Internet connection")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                m_data_show.clear()
                fetchLocationPredictions(query)

            }
        })

        autocm.apply {
            threshold = 0
//            val fields = listOf(Place.Field.ID, Place.Field.NAME)
//            val suggestAdapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, fields )
//            setAdapter(suggestAdapter)
            this.setOnClickListener {
                showDropDown()
            }
            //  performCompletion()
        }
        binding.destination.setOnClickListener {
//            openlocation()
            updateDatePicker()
        }



        searhhotel.setOnClickListener {

            val searchView = autocm



            if (searchView.text.isNullOrEmpty().not() && guestLiveData.value != null && dateLiveData.value != null) {
                val newFragment = HotelListFragment()
                var testitem = Pair(searchView.text.toString(),null)
//                contains(namePlace(testitem).item.first.trim()).not()

                if (m_data_show.contains(searchView.text.toString()).not() ){
//                    Toast.makeText(requireContext(), "Please Enter CorrectData!!", Toast.LENGTH_SHORT).show()
                    searchView.text.clear()
                    searchView.setError("Please enter correct Address")
//                    Toast.makeText(requireContext(), "wrong data", Toast.LENGTH_SHORT).show()

                } else {
                    val args = Bundle()
//                args.putParcelable("guest", guestLiveData.value)
//                args.putString("date",dateLiveData.value)
                    args.putString("query", searchView.text.toString())
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


            } else {
                autocm.setError("Please your hotel")
//                Toast.makeText(requireContext(), "Please search query", Toast.LENGTH_SHORT).show()
            }
            m_data_show.clear()
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
    @SuppressLint(
        "InflateParams", "MissingInflatedId", "SimpleDateFormat", "ResourceAsColor",
        "RestrictedApi", "NotifyDataSetChanged"
    )
    private fun bottomTask(view: View) {

        var selectdate = binding.linearDate
        var selectGuest = binding.linearGuest

//        var dayOfMonth0 = 0
//        var month0 = 0
//        var year0 = 0
        view.setOnClickListener {
            val room_count = share_pref.getInt("room", 1)
            val adultv_count = share_pref.getInt("adults", 1)
            val childrens_count = share_pref.getInt("childrens", 0)
            when (view) {
                selectdate -> {
//                    updateDatePicker()
                    val builder = MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select Date Range")
                    val fresh = share_pref.getLong("startdate", Date().time)
                    val fresh1 = share_pref.getLong("endDate", Date().time)
                    builder.setSelection(androidx.core.util.Pair(fresh, fresh1))
                    val datePicker = builder.build()

//                    if (datePicker.)
//                    Allfun.freshdate()

                    datePicker.addOnPositiveButtonClickListener { selection ->
                        val startDate = selection.first
                        val endDate = selection.second

                        editor.putLong("startdate", startDate)
                        editor.putLong("endDate", endDate)
                        editor.apply()
                        dateModifier(Pair(startDate, endDate))

                        val calendar = Calendar.getInstance()

// Set the start date of the range to 1 week ago
                        calendar.add(Calendar.DAY_OF_MONTH, -7)


                        val date = Date(startDate)
                        var date_one_milli = date.time
                        val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
                        var startdate0 = dateFormat.format(date)

                        val date2 = Date(endDate)
                        var date_two_milli = date2.time
                        val dateFormat2 = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())

                        var enddate0 = dateFormat2.format(date2)
                        var rangestr = "$startdate0 - $enddate0"
                        binding.rangeDAteTextView.setText(rangestr)


                        Log.e("millis_date", "bottomTask: $date_one_milli,$date_two_milli")
                        dateLiveData.value = rangestr
                    }

//                    datePicker
                    datePicker.show(childFragmentManager, "DateRangePicker")

                }


                selectGuest -> {

                    Log.e("shref_datya", "bottomTask: $room_count,$adultv_count,$childrens_count")

                    var bottomSelectGuest = BottomSheetDialog(requireContext())


                    val roomcount = bottomSelectGuest.findViewById<TextView>(R.id.roomcount)
                    val adultcount = bottomSelectGuest.findViewById<TextView>(R.id.adultcount)
                    val childcount = bottomSelectGuest.findViewById<TextView>(R.id.childrencount)


                    var view1 = layoutInflater.inflate(R.layout.bottom_sheet_gestlist, null, false)
                    bottomSelectGuest.setContentView(view1)

                    roomcount?.setText(room_count.toString())
                    adultcount?.setText(adultv_count.toString())
                    childcount?.setText(list.size)

                    bottomSelectGuest.show()
                    bottomSelectGuest.setCancelable(false)
                    var closebtn = view1.findViewById<MaterialCardView>(R.id.close_selectGuest)
                    var rc = view1.findViewById<RecyclerView>(R.id.children_recyclerView_guestList)
                    adapter = guest_Child_Adapter(list, requireContext())
                    rc.adapter = adapter
                    adapter.notifyDataSetChanged()

//                    var spinner1 = view1.findViewById<Spinner>(R.id.spinner)    //spinner 1
//                    var spinner2 = view1.findViewById<Spinner>(R.id.spinner2)     //spinner2
////                    R.string.chip2
//                    var languages = resources.getStringArray(R.array.Languages)
//                    if (spinner1!=null && spinner2!=null){
//                        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,languages)
//                        spinner1.adapter = adapter
//                        spinner2.adapter  = adapter
//                    }


                    closebtn.setOnClickListener {
                        bottomSelectGuest.dismiss()
                        childc  = final_count
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


//        var room_count =share_pref.getInt("room",1)
//        var adultv_count = share_pref.getInt("adults",1)
//        var childrens_count = share_pref.getInt("childrens",0)
//
//        roomcount?.setText(room_count.toString())
//        adultcount?.setText(adultv_count.toString())
//        childcount?.setText(childrens_count.toString())

        roomdata(addroom, roomsub, roomcount)
        adultdata(addadult, roomadult, adultcount)
        childdata(addchild, roomchild, childcount)
        var submit = view?.findViewById<MaterialButton>(R.id.guestApply)

        submit?.setOnClickListener {
//            Toast.makeText(requireContext(), "  Guest Are  R:$roomc,A:$adultc,C:$childc, ", Toast.LENGTH_SHORT).show()
            view?.dismiss()

            val Guest = "${adultc} Guests, ${roomc} Rooms"
            var m_guest_ = setOf(adultc.toString(), roomc.toString())

            editor.putInt("guest_count", adultc)
            editor.putInt("room_count", roomc)
            editor.apply()

            binding.GuestTextView.setText(Guest)

            guestLiveData.value = guestdatacls(adultc, roomc)
            Log.e("livedata", " ${guestLiveData.value} ")
//            liveData

            Log.e("share+phref", "guestDataHandler: $roomc,$adultc,$childc")
//            editor.remove("room")
//            editor.remove("adults")
//            editor.remove("childrens")
//            editor.apply()

            editor.putInt("room", roomc)
            editor.putInt("adults", adultc)
            editor.putInt("childrens", list.size)

//            editor.putInt("childrens",childc)
            editor.apply()
            final_count = childc

        }


    }

    private fun roomdata(addView: ImageView?, subView: ImageView?, textshowview: TextView?) {
        var totalRoom = share_pref.getInt("room", 1)
        textshowview?.setText(totalRoom.toString())
        addView?.setOnClickListener {

            totalRoom += 1
            textshowview?.setText(totalRoom.toString())
            roomc = totalRoom

        }
        subView?.setOnClickListener {
            if (totalRoom == 0) {
                textshowview?.setText("0")
            } else {
                totalRoom -= 1
                textshowview?.setText(totalRoom.toString())
            }
            roomc = totalRoom
        }


    }

    private fun adultdata(addView: ImageView?, subView: ImageView?, textshowview: TextView?) {

        var count1 = share_pref.getInt("adults", 1)
        textshowview?.setText(count1.toString())

        addView?.setOnClickListener {
            count1 += 1
            textshowview?.setText(count1.toString())
            adultc = count1
        }

        subView?.setOnClickListener {


            if (count1 == 0) {
                textshowview?.setText("0")
            } else {
                count1 -= 1
                textshowview?.setText(count1.toString())
                adultc = count1
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun childdata(addView: ImageView?, subView: ImageView?, textshowview: TextView?) {


        var count1 = final_count
        if (list.isNullOrEmpty().not() ) {

                if (count1!=list.size){
                    list.clear()
                    for (i in 0 until count1){
                        list.add(guest_Children(textView,spinner))

                    }
                    adapter.notifyDataSetChanged()
                }
            Log.e("list_check", "childdata: listsize${list.size},count : $count1",)

//
        } else {
            for (i in 0 until count1){
                list.add(guest_Children(textView,spinner))
                Log.e("check_child_data", "childdata: if block executes$i", )
            }
        }
        adapter.notifyDataSetChanged()
        textshowview?.setText(count1.toString())

        addView?.setOnClickListener {

            count1 += 1
            textshowview?.setText(count1.toString())
            childc = count1
//            child_handling()

            list.add(guest_Children(textView, spinner))

            adapter.notifyDataSetChanged()

        }

        subView?.setOnClickListener {


            if (count1 == 0) {
                textshowview?.setText("0")
            } else {
                count1 -= 1
                textshowview?.setText(count1.toString())
                childc = count1

//                if (list.contains(guest_Children(textView,spinner))){
//                    list.removeAt(list.size-1)
//                    adapter.notifyDataSetChanged()
//                }else{
//                    list.removeAt(list.size-1)
//                    adapter.notifyItemRemoved(list.size-1)
//                    adapter.notifyDataSetChanged()
//                }

                if (list.size >= 1) {
                    list.removeAt(list.size - 1)
                    adapter.notifyItemRemoved(list.size - 1)
//                    adapter.notifyDataSetChanged()
                } else if (list.size == 0) {
                    list.clear()
                    adapter.notifyDataSetChanged()
                } else {
                    list.clear()
                    adapter.notifyDataSetChanged()
                }


            }
        }
        Log.e("child_data", "childdata: ${list.size},$childc,$count1")
    }

    @SuppressLint("SetTextI18n")
//    fun checklivedata(m1:MutableLiveData<guestdatacls>?, m2:MutableLiveData<String>?){
//
//        var TAG = "test31"
//        if (m1?.value != null && m2?.value != null){
//
//            var data = guestLiveData.value
//
//            val adlt = data?.guest
//            val room = data?.rooms
////            binding.GuestTextView.setText("$adlt Guest,$room Rooms")
//            binding.rangeDAteTextView.setText(dateLiveData.value)
//            Log.e(TAG, "checklivedata: !null")
//
//        }
//        else{
//            guestLiveData.value = guestdatacls(1,1)
//            dateLiveData.value = "Sun, 8 jun - Sun, 8 Jun "
////        binding.GuestTextView.setText("1 Guest,1 Room")
////        binding.rangeDAteTextView.setText("8 jun - 8 Jun")
//            Log.e(TAG, "checklivedata: null ${guestLiveData.value},${dateLiveData.value}  ")
//        }
//    }
//    @SuppressLint("ResourceType")
    private fun addAddress() {


    }

    private fun initView() {


        if (!Places.isInitialized()) {
            Places.initialize(
                requireContext(), getString(R.string.google_maps_key3)
            )
        }
        placesClient = Places.createClient(requireContext())
    }

    private fun openlocation() {


//        val fields = listOf(Place.Field.ID, Place.Field.NAME)
//
//        // Start the autocomplete intent.
//        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
//            .build(requireActivity())
//        startActivityForResult(intent, AllConstants.REQUEST_CODE.AUTOCOMPLETE_REQUEST_CODE)
////


    }

    private fun showPredictions(predictions: List<AutocompletePrediction>) {

//        Log.e("predictions", "showPredictions: ${predictions.get(0)},", )
        val colorSpan = ForegroundColorSpan(Color.RED)
        val spannableString = SpannableString("Hello World")
        spannableString.setSpan(colorSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


        val colorSpans =
            spannableString.getSpans(0, spannableString.length, ForegroundColorSpan::class.java)
        var m_predictions = predictions.forEach {
//           it.placeId
            Log.e("m_predictions", "showPredictions: m_predictions")


           val curr_item =  Pair(it.getFullText(colorSpan).toString(),it.placeId)

//            m_data_show.add(namePlace(curr_item))
            m_data_show.add(it.getFullText(colorSpan).toString())
        }

        val suggestAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_dropdown_item_1line, m_data_show) }
        binding.AutoCompleteTextView.setAdapter(suggestAdapter)
        suggestAdapter?.notifyDataSetChanged()
        Log.e("m_predictions", "showPredictions:$m_data_show ")


    }

    @SuppressLint("LongLogTag")
    private fun fetchLocationPredictions(query: String) {
        val token = AutocompleteSessionToken.newInstance()
//        val bounds = RectangularBounds.newInstance(
//            LatLng(placesClient., SW_LONGITUDE),
//            LatLng(NE_LATITUDE, NE_LONGITUDE)
//        )

        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(token)
            .setQuery(query)
//            .setLocationBias(bounds)
            .build()


        val placesClient = Places.createClient(requireContext())
        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                val predictions = response.autocompletePredictions
                showPredictions(predictions)
            }
            .addOnFailureListener { exception: Exception ->
                // Handle API call failure
                Log.e(
                    "fetchLocationPredictions",
                    "fetchLocationPredictions: ${exception.message} ",
                )
            }
    }

    fun getpositionData(list: MutableList<View>) {

        for (item in list) {

            item.setOnClickListener { item.id }

        }
    }
//
//    fun getLatLng(locationName: String): Pair<Double, Double>? {
//        val context = GeoApiContext.Builder()
//            .apiKey(getString(R.string.google_maps_key))
//            .build()
//        val results: Array<GeocodingResult> = GeocodingApi.geocode(context, locationName).await()
//        if (results.isNotEmpty()) {
//            val latLng = results[0].geometry.location
//            return Pair(latLng.lat, latLng.lng)
//        }
//        return null
//    }


//    private fun fetchPlacePredictions(query: String) {
//        val request = FindAutocompletePredictionsRequest.builder()
//            .setQuery(query)
//            .setTypeFilter(TypeFilter.ADDRESS)
//            .build()
//
//        placesClient.findAutocompletePredictions(request)
//            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
//                for (prediction: AutocompletePrediction in response.autocompletePredictions) {
//                    // Handle each prediction as needed
//                    val placeId = prediction.placeId
//                    val description = prediction.getPrimaryText(null).toString()
//                    Log.d("fetchplacepred", "Place: $placeId, Description: $description")
//                }
//            }
//            .addOnFailureListener { exception: Exception ->
//                // Handle error
//                Log.e("fetchplacepred", "Place prediction fetch failed: ${exception.message}")
//            }
//    }

    private fun updateDatePicker() {

        val startDate = share_pref.getLong("startdate", Date().time) // start date in milliseconds
        val endDate: Long = share_pref.getLong("endDate", Date().time)// end date in milliseconds


// Get the current calendar instance
        val calendar = Calendar.getInstance()

// Set the start date of the range to 1 week ago
        calendar.add(Calendar.DAY_OF_MONTH, -7)


// Create a MaterialDatePicker with a date range
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setSelection(androidx.core.util.Pair(startDate, endDate))

// Show the MaterialDatePicker
        val materialDatePicker = builder.build()
        materialDatePicker.show(childFragmentManager, "DATE_PICKER_TAG")

    }

    //    private fun child_handling():MutableList<guest_Children>{
////        val child_count  = share_pref.getInt("childrens",0)
//
//
//
//        for (item in 1 until child_count){
////            textView.id = textView.hashCode()
//            var current_guest_item = guest_Children(textView,spinner)
//            list.add(current_guest_item)
//        }
//        return list
//    }
    private fun dateModifier(pair: Pair<Long, Long>) {
        val date = Date(pair.first)
        val date1 = Date(pair.second)
//    var date_one_milli = date.time
        val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
        var startdate0 = dateFormat.format(date)
        var startdate1 = dateFormat.format(date1)
        Log.e("detes_setuop", "dateModifier: ")
        var date_setup = "$startdate0 - $startdate1"
        editor.putString("selected_date", date_setup)
    }
    fun getTopHotels() {

        Log.e("TAG", "getTopHotels: $", )
        try {

            val geo = GeoApiContext.Builder()
                .apiKey(getString(R.string.google_maps_key3))
                .build()


//        31.1471° N latitude and 75.3412° E longitude.
            var demolat = com.google.maps.model.LatLng(31.1471,75.3412)   //india
            val topHotel = PlacesApi.nearbySearchQuery(geo,demolat).keyword("top hotel,best Hotel" ).location(demolat).rankby(RankBy.PROMINENCE).maxPrice(PriceLevel.VERY_EXPENSIVE).type(PlaceType.LODGING).await()

            var a =  topHotel.results
            a.forEach {
                Log.e("topHotell", "getTopHotels: $it", )
            }
        }
        catch (e:Exception){
            Log.e("exception_occurs", "getTopHotels:${e.message},${e.cause}", )
        }

    }
}

//}


