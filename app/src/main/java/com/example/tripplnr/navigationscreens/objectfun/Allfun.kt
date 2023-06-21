package com.example.tripplnr.navigationscreens.objectfun

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.DataCls.guestdatacls
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.hotelListFragment.HotelListFragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.maps.GeocodingApi
import com.google.type.LatLng
import java.text.SimpleDateFormat
import java.util.*


object Allfun {
    var currencyData = MutableLiveData<String>("USD")

    val dateLiveData =  MutableLiveData<String>(freshdate())

     val guestLiveData =  MutableLiveData<guestdatacls>(guestdatacls(1,1))



    fun task1(){
        Log.e("testlog", "task1: i am a log", )
        println("testobj")
    }
    fun freshdate():String{
        val date = Date()
        val dateFormat = SimpleDateFormat("EEE ,dd MMM", Locale.getDefault())
        var startdate0 = dateFormat.format(date)


        var rangestr = "$startdate0 - $startdate0"

        return rangestr
    }
    @SuppressLint("CommitTransaction")
    fun openFragment(
        tofragment: Fragment,
        fragmentManager: FragmentManager,

    ){

        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace with your actual fragment class

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, tofragment)
        transaction.addToBackStack(null)
        transaction.setReorderingAllowed(true)
        transaction.commit()
    }













    fun loginUser(email:EditText,passworde:EditText,context:Context,loginbtn:MaterialButton){
        //    private lateinit var binding1:ActivitySignUpBinding


             var auth:FirebaseAuth = Firebase.auth

            loginbtn.setOnClickListener {
//                val userName = email.text.toString()
//                val password0 = password.text.toString()
//
//                if (userName.isNullOrEmpty().not() && password0.isNullOrEmpty().not() ) {
//                    auth.signInWithEmailAndPassword(userName, password0)
//                        .addOnCompleteListener { task: Task<AuthResult> ->
//
//                                if (task.isSuccessful) {
//                                    Toast.makeText(
//                                        context,
//                                        "You loged successfully",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                } else {
//                                    Toast.makeText(context, "Wrong Password!", Toast.LENGTH_SHORT)
//                                        .show()
//                                    Log.e("tag12", "loginUser: ${task.exception} ", )
//                                }
//
//                        }
//
//
////                    Firebase.auth.signInWithEmailAndPassword(userName, password0)
////                        .addOnCompleteListener { task: Task<AuthResult> ->
////                            if (task.isSuccessful) {
////                                // Login successful
////                                val user: FirebaseUser? = auth.currentUser
////                                // Proceed with further actions
////                                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
////
////                            } else {
////                                // Login failed
////                                val exception = task.exception
////                                // Handle the error
////                                Toast.makeText(context, "$exception", Toast.LENGTH_SHORT).show()
////                                Log.e("TAG12", "loginUser: $exception", )
////
////                            }
////                        }
//                }
//                else{
//                    Toast.makeText(context, "Please Fill Blank!", Toast.LENGTH_SHORT).show()
//                }


                var firebaseAuth = FirebaseAuth.getInstance()


                    val userName = email.text.toString()
                    val password = passworde.text.toString()

                    if (userName.isNullOrEmpty().not() && password.isNullOrEmpty().not()){
                        firebaseAuth.signInWithEmailAndPassword(userName,password).addOnCompleteListener {
                            if (it.isSuccessful){
//                                val intent = Intent(this@SignInActivity,FinalActivity::class.java)
//                                startActivity(intent)
                                Toast.makeText(context, "login!", Toast.LENGTH_SHORT).show()
                                Log.e("TAG12", "loginUser: login  ", )

                            }
                            else{
                                Toast.makeText(context, "Wrong Password!", Toast.LENGTH_SHORT).show()
                                Log.e("TAG12", "loginUser: wrong password  due to: ${it.exception}", )
                            }
                        }
                    }
                    else{
                        Toast.makeText(context, "Please Fill Blank!", Toast.LENGTH_SHORT).show()
                        Log.e("TAG12", "loginUser: Please Fill Blank", )
                    }





            }






    }

    fun textfChar(data:String):List<Char>{

        var words = data.split(" ")
        var fChar = mutableListOf<Char>()

        for (word in words){
            if (word.isNullOrEmpty()){
                fChar.add(word[0])
            }
        }

        return fChar
    }
    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")
        var a =emailRegex.matches(email)
        Log.e("TAG", "isEmailValid: $a", )
        return a
    }
    fun isUserLoggedIn(auth: FirebaseAuth): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    fun openWeb(context:Context){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse( "https://www.booking.com"))
        context.startActivity(intent)
    }
//        fun getLocationLatLng(locationName: String): LatLng? {
//        val geocoder = GeocodingApi.newRequest(getGeoContext()).address(locationName)
//        val result = geocoder.await()
//
//        if (result.isNotEmpty()) {
//            val location = result[0].geometry.location
//            return LatLng(location.lat, location.lng)
//        }
//
//        return null
//    }
//
//
//    fun getGeoContext(): GeoApiContext {
////        val apiKey = getString(R.string.satwinder_shergill)
////        val apiKey = "AIzaSyBq16ekrXE3LHeDIwu3KDk0O9s-rMjZpqc"
//        val apiKey = "AIzaSyBOdO2-t1GISAkfwBf1hHXmrc6YAhkX1zA"
//        return GeoApiContext.Builder().apiKey(apiKey).build()
//    }
//val latLng = getLocationLatLng(locationName)
//
//    if (latLng != null) {
//
//        val latitude = latLng.latitude
//        val longitude = latLng.longitude
//
//        Log.e("latlng", "onViewCreated:Latitude: $latitude, Longitude: $longitude ", )
//
//    } else {
//        Log.e("latlng", "onViewCreated:Latitude: null", )
//    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

}
object FirebaseUtils {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
}
object mLive{
    var data = MutableLiveData<List<travelBlogItem>>()

    init {
        initlizeData()
    }
    fun initlizeData(){
        data.value = dataformLive()
    }

    fun addtofavorate(item: travelBlogItem) {
        data.value?.forEach {
            if (it == item) {
                it.isfavorate = true
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun removefromfavorate(item: travelBlogItem) {
        data.value?.forEach {
            if (it == item) {

                it.isfavorate = false

//                adapter.notifyDataSetChanged()
            }


        }

    }

    fun dataformLive():List<travelBlogItem>{
        val  list  = listOf<travelBlogItem>(

            travelBlogItem(
                R.drawable.blog2,"the Royal Temple","12 may 23 ","1.35s",
                "Wat Phra Kaew (Temple of the Emerald Buddha) - Located within the Grand Palace complex in Bangkok, Thailand, this temple is considered one of the most sacred sites in Thai Buddhism and is closely associated with the Thai royal family.", isfavorate = false),
            travelBlogItem(
                R.drawable.blog1,"the Swanrana mandhir ","12 may 23 ","1.11s",
                " \"Swarana Mandhir\" or \"Swarna Mandir\" in relation to a royal temple. It's possible that the term may refer to a temple with a golden or gilded appearance, as \"Swarana\" or \"Swarna\" means \"golden\" in certain languages.", isfavorate = true),

            travelBlogItem(
                R.drawable.gt,"the Golden Temple","12 may 23 ","1.32s",
                "The Golden Temple, also known as Sri Harmandir Sahib or Darbar Sahib, is a prominent Sikh gurdwara (place of worship) located in Amritsar, Punjab, India. It is one of the most important and spiritually significant pilgrimage sites for Sikhs worldwide.", isfavorate = true),

            travelBlogItem(
                R.drawable.blog4,"the love city","12 may 23 ","12.32s",
                "The term \"Love City\" is a general term that can refer to different cities around the world known for their association with love, romance, or a romantic atmosphere. There are several cities that are often referred to as \"Love Cities\" due to their romantic ambiance, scenic beauty, or historical connections to love and romance.", isfavorate = false),
            travelBlogItem(
                R.drawable.blog4,"the Punjaab","12 may 23 ","59.32s",
                "Punjab is a region in South Asia that is divided between two countries: India and Pakistan. It is a culturally rich and historically significant region known for its vibrant traditions, agricultural prosperity, and religious diversity.", isfavorate = true),

            )
        return list
    }
}