package com.example.tripplnr.navigationscreens.Search.hotel

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.SearchRecentSuggestionsProvider
import android.database.Cursor
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.get
import androidx.core.view.marginBottom
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
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.RangeDateSelector
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

const val API_KEY = "AIzaSyAsSMYb29gSo5bq2Ip-3lwx5mhqToxd5W8"
const val API_KEY_ONE = "AIzaSyAA8J4FwN3vqvltGu5osXTtKeexjjq_CkA"

const val NEW1_API_KEY = "AIzaSyBiWumyUM7nfC4_SlLP1j-FIsDuyLpb390"
const val NEW2_API_KEY = "AIzaSyBBgOn1FtmzyjUnX0Xl6xMFqXYFmSEgdZg"

class HotelsFragment : Fragment(){
    private lateinit var binding: FragmentHotelsBinding
    private lateinit var rc: RecyclerView
    var roomc:Int = 1
    var adultc:Int = 1
    var childc:Int = 1







    private lateinit var placesClient:PlacesClient
//    private lateinit var suggestionAdapter: SuggestionAdapter
//    private lateinit var searchView :EditText



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHotelsBinding.inflate(layoutInflater)
        initView()


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
//        Log.e("isApiAuthorized", "onViewCreated: ${isApiAuthorized()}")
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


        Places.initialize(requireContext(), getString(R.string.google_maps_key))
        placesClient = Places.createClient(requireContext())
       var autocm =  binding.AutoCompleteTextView


//        autocm.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,placesClient)
        autocm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (Allfun.isInternetAvailable(requireContext())==true.not()){
                    autocm.setError("check Internet connection")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                fetchLocationPredictions(query)
            }
        })
        autocm.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val selectedPlace = parent.getItemAtPosition(position)


            val locationName = "$selectedPlace"

        }



//        var listcities = listOf("Amritsar","Ajnala","SAS Nagar","SBS Nagar","Chandighar","Pathankot","Himachal","Mohali","Ropar","Kharar","Tarntaran","Gurdaspur")





        autocm.apply {
            threshold = 0
//            val fields = listOf(Place.Field.ID, Place.Field.NAME)
//            val suggestAdapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, fields )
//            setAdapter(suggestAdapter)
            this.setOnClickListener{
                showDropDown()
            }
            //  performCompletion()
        }
        binding.destination.setOnClickListener {
            openlocation()
        }



        search.setOnClickListener {

            val searchView = autocm


            if (searchView.text.isNullOrEmpty().not() && guestLiveData.value!=null && dateLiveData.value!=null   ){
                val newFragment = HotelListFragment()

//                if (listcities.contains(searchView.text.toString()).not()){
////                    Toast.makeText(requireContext(), "Please Enter CorrectData!!", Toast.LENGTH_SHORT).show()
//                    searchView.text.clear()
////                    searchView.setError("Please enter correct city ")
//                    Toast.makeText(requireContext(), "wrong data", Toast.LENGTH_SHORT).show()
//
//                }else{
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
//                }


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
    @SuppressLint("InflateParams", "MissingInflatedId", "SimpleDateFormat", "ResourceAsColor",
        "RestrictedApi"
    )
    private fun bottomTask(view: View) {

        var selectdate = binding.linearDate
        var selectGuest = binding.linearGuest

        var dayOfMonth0 = 0
        var month0 = 0
        var year0 = 0
        view.setOnClickListener {
            when (view) {
                selectdate -> {

                    val builder = MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select Date Range")
                    val datePicker = builder.build()

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

//                    datePicker
                    datePicker.show(childFragmentManager, "DateRangePicker")

//                    var dp = DatePickerDialog(requireContext())
//                    dp.datePicker.minDate
//                    dp.show()





                }


                selectGuest -> {

                    var bottomSelectGuest = BottomSheetDialog(requireContext())
                    var view1 = layoutInflater.inflate(R.layout.bottom_sheet_gestlist, null, false)
                    bottomSelectGuest.setContentView(view1)
                    bottomSelectGuest.show()
                    var closebtn = view1.findViewById<MaterialCardView>(R.id.close_selectGuest)
                    var spinner1 = view1.findViewById<Spinner>(R.id.spinner)    //spinner 1
                    var spinner2 = view1.findViewById<Spinner>(R.id.spinner2)     //spinner2
//                    R.string.chip2
                    var languages = resources.getStringArray(R.array.Languages)
                    if (spinner1!=null && spinner2!=null){
                        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,languages)
                        spinner1.adapter = adapter
                        spinner2.adapter  = adapter
                    }
//                    spinner1.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
//                        override fun onItemSelected(
//                            parent: AdapterView<*>?,
//                            view: View?,
//                            position: Int,
//                            id: Long
//                        ) {
//                            Toast.makeText(requireContext(),
//                                getString(R.string.selected_item) + " " +
//                                        "" + languages[position], Toast.LENGTH_SHORT).show()
//                        }
//
//                        override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                        }
//
//                    }
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
//    val fields = listOf(Place.Field.ID, Place.Field.NAME)
//
//    val intent1 = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
//        .build(requireActivity())
//    startActivityForResult(intent1, AllConstants.REQUEST_CODE.AUTOCOMPLETE_REQUEST_CODE)
//
//    Log.e("field12", "addAddress: ${fields[1].name}")


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
    private fun openlocation(){


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
        spannableString.setSpan(colorSpan, 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


        val colorSpans = spannableString.getSpans(0, spannableString.length, ForegroundColorSpan::class.java)
        var m_data_show = mutableListOf<String>()
       var m_predictions = predictions.forEach {
//           it.placeId


           m_data_show.add(it.getFullText(colorSpan).toString())
        }

        val suggestAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, m_data_show )
        binding.AutoCompleteTextView.setAdapter(suggestAdapter)
        suggestAdapter.notifyDataSetChanged()
        Log.e("m_predictions", "showPredictions:$m_data_show ", )



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
                Log.e("fetchLocationPredictions", "fetchLocationPredictions: ${exception.message} ", )
            }
    }

    fun getpositionData(list:MutableList<View>){

        for (item in list){

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

    fun exceptioncode(e:Exception){
        Log.e("exception1324", "exceptioncode:  ${e.message} ", )


    }

}

//}



