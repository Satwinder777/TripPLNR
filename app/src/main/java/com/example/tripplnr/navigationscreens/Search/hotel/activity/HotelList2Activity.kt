package com.example.tripplnr.navigationscreens.Search.hotel.activity


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tripplnr.R
import com.google.maps.model.LatLng
import com.example.tripplnr.databinding.ActivityHotelList2Binding
import com.example.tripplnr.navigationscreens.ApiHandling.MyApiService
import com.example.tripplnr.navigationscreens.ApiHandling.Near_Location
import com.example.tripplnr.navigationscreens.DataCls.MyDataHandle
import com.example.tripplnr.navigationscreens.DataCls.lat_data
import com.example.tripplnr.navigationscreens.DataCls.my_latlng
import com.example.tripplnr.navigationscreens.Search.hotel.activity.adapter.HotelList2Adapter
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions

import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.GeoApiContext
import com.google.android.libraries.places.api.model.Place
import com.google.firebase.database.core.view.DataEvent
import com.google.maps.PlacesApi
import com.google.maps.model.PlaceType
import com.google.maps.model.PlacesSearchResult
import com.google.maps.model.PriceLevel
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


import java.io.IOException
import java.util.ArrayList


class HotelList2Activity : AppCompatActivity(),HotelList2Adapter.onclickViewDeal,OnMapReadyCallback {
    private lateinit var binding :ActivityHotelList2Binding
    private lateinit var geoApiContext: GeoApiContext
    private lateinit var mMap :GoogleMap
    private lateinit var placesClient : PlacesClient
    private lateinit var fusedLocationClient :FusedLocationProviderClient
    private lateinit var latLng : com.google.android.gms.maps.model.LatLng
    private lateinit var rc : RecyclerView
    private lateinit var adapter : HotelList2Adapter
    private val PERMISSION_REQUEST_CODE = 123
    private var parcelableList = java.util.ArrayList<MyDataHandle>()



    var list_loc = mutableListOf<lat_data>()
    var rclist = mutableListOf<PlacesSearchResult>()
//    var m_item = mutableSetOf<LatLng>()


    @SuppressLint("MissingInflatedId", "InflateParams")
    private lateinit var searchquery:String
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelList2Binding.inflate(layoutInflater)
        setContentView(binding.root)
//        var arr =  ArrayList<MyDataHandle>()

        locationlistner()

      parcelableList = intent.getParcelableArrayListExtra<MyDataHandle>("PlaceSearchResult") as ArrayList<MyDataHandle>
       var lt = intent.getParcelableExtra<my_latlng>("latLngKey")
//        intent .getextra

        Log.e("lat_lng", "onCreate: $lt", )

    if (lt != null) {
        if (lt.data!=null){
        latLng = com.google.android.gms.maps.model.LatLng(lt.data!!.latitude,lt.data!!.longitude)
    }

}


        if (  parcelableList.isNullOrEmpty().not()){
            parcelableList.forEach {


                if (it.data!=null){
                    rclist = it.data.toMutableList()
                }
                else{
                    Log.e("my_dataparcel", "onCreate: nulllllllllll", )

                }



            }
            Log.e("my_dataparcel", "onCreate000: data found!1", )

        }
        else{
            Log.e("my_dataparcel", "onCreate000: nulllllllllll", )

        }






        binding.shimmerHotelPrediction.startShimmer()

        val mapFragment = supportFragmentManager.findFragmentById(com.example.tripplnr.R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//            PERMISSION_REQUEST_CODE
//        )


        rc = binding.hotelListRecyclerview2

//        rc.adapter = adapter
//        adapter.notifyDataSetChanged()


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.filterbottom.setOnClickListener {

            val bottomSheetFragment = FilterBottomSheet()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            bottomSheetFragment.isCancelable = false

        }
        binding.showList.setOnClickListener {

            onBackPressed()

        }
        binding.closebtnHotelList2.setOnClickListener{
           onBackPressed()
        }
        searchquery = intent.getStringExtra("query").toString()
//        var location_m = getLocationFromAddress(searchquery)
//        Log.e("location_m", "onCreate: location is $location_m", )

        var rangedate = Allfun.dateLiveData.value
        binding.cityname.setText(searchquery)
        binding.daterange.setText(rangedate)


        var guestdateails = Allfun.guestLiveData.value
        var guest = guestdateails?.guest
        var room = guestdateails?.rooms
        binding.roomcountMapFrag.setText(room.toString())
        binding.guestcountMapFragment.setText(guest.toString())
        val apiKey = getString(com.example.tripplnr.R.string.google_maps_key3)
        geoApiContext = GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()



        //AIzaSyBq16ekrXE3LHeDIwu3KDk0O9s-rMjZpqc

        Places.initialize(this, getString(com.example.tripplnr.R.string.google_maps_key3))
        placesClient = Places.createClient(this)

        // Call the function to search for a location and find the nearest hotel






    }


    override fun viewDealHandle(position: Int, view: View) {
        Allfun.openWeb(this)
    }



    @SuppressLint("NotifyDataSetChanged")
    override fun onMapReady(map: GoogleMap) {
        mMap = map



        if (isLocationEnabled(this)){

            adapter = HotelList2Adapter(rclist,this,mMap,latLng)
            rc.adapter = adapter
            adapter.notifyDataSetChanged()


//            Toast.makeText(this , "already granted!", Toast.LENGTH_SHORT).show()
//            searchLocation(searchquery)
//            findNearestHotel( )
//            getHotels()

            binding.shimmerHotelPrediction.visibility = View.GONE
            binding.hotelListRecyclerview2.visibility = View.VISIBLE
            buildCircle()

        }
        else{
            Toast.makeText(this, "location is off", Toast.LENGTH_SHORT).show()
        }
    }


//    fun searchLocation(location:String) {
//
//        var addressList: List<Address>? = null
//        if (location != null || location != "") {
//            val geocoder = Geocoder(this)
//            try {
//                addressList = geocoder.getFromLocationName(location, 1)
//                Log.e("lat_lng_data", "searchLocationlist: $addressList")
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//            val address = addressList!![0]
//            latLng = LatLng(address.latitude, address.longitude)
//
//
//        }
//
//    }


    @SuppressLint("NotifyDataSetChanged")
//    private fun findNearestHotel( ) {
//        var tag = "findNearestHotel"
//
//        val placeFields = listOf(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.TYPES)
//        val request = FindCurrentPlaceRequest.builder(placeFields)
//
//            .build()
//
//
//       if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && isLocationEnabled(this)) {
//           var count =0
//            placesClient.findCurrentPlace(request)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val placesResponse = task.result
//
//                        val filteredPlaces = placesResponse?.placeLikelihoods?.filter {
//                            it.place.types?.contains(Place.Type.ESTABLISHMENT) == true
//                        }
//                        Log.e(tag, "findNearestHotel0: $filteredPlaces ")
//
//
//
//                    filteredPlaces?.forEach {
//                         var lg = it.place.latLng
//                         var lt = it.place.name
//                        if (lg!=null||lt.isNullOrEmpty()){
//                            var data = lat_data(lg!!,lt!!)
//                            list_loc.add(data)
//                            mMap.isMyLocationEnabled = true
//
//                        }
//                    }
//
//                        val location_m = isLocationEnabled(this)
//                        if (location_m){
//                            getHotels()
//
//                        }else{
//                            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
//                        }
//
//                    } else {
//                        // Handle the task exception
//                        val exception = task.exception
//                        Log.e(tag, "findNearestHotel_exp: ${exception?.message} ")
//
//                    }
//                }
//
//           Log.e(tag, "findNearestHotel: $count")
//        }
//        else{
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
//        }
//
//
//    }


//    private fun funcall(){
//
//        val apiKey = "AIzaSyBq16ekrXE3LHeDIwu3KDk0O9s-rMjZpqc"
//        var location = latLng // e.g., "37.7749,-122.4194"
//        val radius = 5000 // in meters
//
//
//        val baseurl = "https://maps.googleapis.com/"
////        json?location=$location&radius=$radius&key=$apiKey
//    val retrofit = Retrofit.Builder()
//        .baseUrl(baseurl) // Replace with your base URL
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val myApiService = retrofit.create(MyApiService::class.java)
//
//
//    var TAG = "funcall"
//    myApiService.getResult(location,radius,apiKey)?.enqueue(object : Callback<Near_Location> {
//
//
//        //            Log.e(TAG, "onFailure: ${t.message},${t.cause}", )
//        override fun onResponse(
//            call: Call<Near_Location>,
//            response: retrofit2.Response<Near_Location>
//        ) {
//            Log.e(TAG, "onResponse: ${response.body()},${response.raw()},${response.errorBody()}", )
//            if (response.isSuccessful){
//                response.body()?.results?.forEach {
//                    Log.e(TAG, "onResponse: $it", )
//                }
//            }
//        }
//
//        override fun onFailure(call: Call<Near_Location>, t: Throwable) {
//            Log.e(TAG, "onFailure: ${t.cause},${t.message}", )
//        }
//
//
//    })
//}



//    @SuppressLint("NotifyDataSetChanged")
//    fun getHotels(){
//        try {
//            var radius = 5000
//            val response = PlacesApi.nearbySearchQuery(geoApiContext, latLng).location(latLng)
//                .type(PlaceType.LODGING,PlaceType.CAFE,PlaceType.BAR)// Specify the type of place you're looking for (e.g., restaurant, cafe, etc.)
//                .radius(radius)
//                .await()
//
//
//            var demo_rad = 3000
//
//            var result  = response.results
//
//
//            if (result!=null) {
////
////
////
////
//                for (data in result) {
//                    var lt = com.google.android.gms.maps.model.LatLng(
//                        data.geometry.location.lat,
//                        data.geometry.location.lng
//                    )
//
//                    if (lt.longitude != null || lt.longitude != null) {
//
////                    rclist.add(data)
//                        Log.e("markdeta", "getHotels: ${data} ",)
//                    }
//
//                    getphotos(data)
//                }
//
//                Log.e("result_size", "getHotels: size result :> ${result.size}",)
//
//            }
//        }
//        catch (e:Exception){
//            Log.e("exception_occurs", "getHotels: ${e.message},${e.cause}", )
//        }
//
//
//
//
//    }
    private fun dummydata():MutableList<PlacesSearchResult>{
        var list  = mutableListOf<PlacesSearchResult>()
        var a = PlacesSearchResult()
//        list.add(a)
        return list
    }
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with your logic
//                Toast.makeText(this, "location Accessed ${grantResults.size}", Toast.LENGTH_SHORT).show()


            } else {
                // Permission denied, handle accordingly (e.g., show an explanation or disable functionality)
                Toast.makeText(this, "location is NotAccessed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getphotos(result: PlacesSearchResult){


    if (result.photos!=null){
        result.photos.forEach {
            Log.e("getphotos", "getphotos: $it,,", )

        }

    }
    else{
        Log.e("getphotos", " nulll getphotos: ", )
        R.string.shareLink

    }

    }
fun buildCircle(){
    try {

        val location_m = isLocationEnabled(this)
        if (location_m){
            val radius = 5000
            val circleOptions = CircleOptions().center(com.google.android.gms.maps.model.LatLng(latLng.latitude, latLng.longitude)).radius(radius.toDouble())
                .strokeWidth(4f).strokeColor(Color.RED).fillColor(Color.argb(50, 255, 0, 0))
            val circle = mMap.addCircle(circleOptions)
            val minZoomLevel = 10.0f // Adjust the minimum zoom level as desired
            val maxZoomLevel = 30.0f // Adjust the maximum zoom level as desired
            mMap.setMinZoomPreference(minZoomLevel)
            mMap.setMaxZoomPreference(maxZoomLevel)
//        if (circle.isVisible.not()){
//            Log.e("check_circle", "buildCircle: circle not visible", )
//            circle.isVisible = true
//            circle.setVisible(true)
//
//// Make the circle transparen
//        }
//        else{
//            Log.e("check_circle", "buildCircle: circle visible", )
//            circle.radius = 5000.0
//
//        }
        }
    }
    catch (e:Exception){
        Log.e("exception_occurs", "buildCircle: ${e.cause}${e.message}", )
    }

}
    private fun locationlistner(){
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Handle location updates
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                // Handle location status changes
                when (status) {
                    LocationProvider.AVAILABLE -> {
                        // Location provider is available
                        // Perform necessary actions
                        Toast.makeText(applicationContext, "$status", Toast.LENGTH_SHORT).show()

                        Log.e("status_check", "onStatusChanged: a$status", )
                    }
                    LocationProvider.OUT_OF_SERVICE -> {
                        // Location provider is no longer available
                        // Perform necessary actions
                        Toast.makeText(applicationContext, "$status", Toast.LENGTH_SHORT).show()

                        Log.e("status_check", "onStatusChanged: o$status", )

                    }
                    LocationProvider.TEMPORARILY_UNAVAILABLE -> {
                        // Location provider is temporarily unavailable
                        // Perform necessary actions
                        Toast.makeText(applicationContext, "$status", Toast.LENGTH_SHORT).show()

                        Log.e("status_check", "onStatusChanged: t$status", )

                    }
                }
            }

            override fun onProviderEnabled(provider: String) {
                // Handle location provider enabled
//                Toast.makeText(applicationContext, "location enabled ${provider.toString()}", Toast.LENGTH_SHORT).show()
                binding.shimmerHotelPrediction.visibility = View.GONE
                binding.shimmerHotelPrediction.startShimmer()
                binding.hotelListRecyclerview2.visibility = View.VISIBLE
            }

            override fun onProviderDisabled(provider: String) {
                // Handle location provider disabled
                binding.shimmerHotelPrediction.visibility = View.VISIBLE
                binding.shimmerHotelPrediction.startShimmer()
                binding.hotelListRecyclerview2.visibility = View.GONE
//                Toast.makeText(applicationContext, "location disabled", Toast.LENGTH_SHORT).show()

            }
        }

// Register the location listener with appropriate provider and minTime and minDistance values
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, proceed with location-related operations
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000L,
                10f,
                locationListener
            )
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        }


    }

}