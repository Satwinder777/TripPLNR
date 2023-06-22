package com.example.tripplnr

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tripplnr.databinding.ActivityMainBinding
import com.example.tripplnr.navigationscreens.Home.NetworkConnectivityListener
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(),
    NetworkConnectivityListener.OnNetworkConnectivityChangeListener {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var binding : ActivityMainBinding
    private lateinit var progressDialog: ProgressDialog

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Checking Internet...")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        var network = NetworkConnectivityListener(this)

        network.startListening(this)




        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        var navController = navHostFragment.navController

        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setBackgroundResource(R.drawable.bg_botton_nav)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)



         // Replace with the ID of your button
        var btn  = bottomNavigationView.findViewById<View>(R.id.homeFragment)
        btn.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }
//        bottomNavigationView.setOnClickListener {
//            when(it.id){
//                R.id.homeFragment->{
//                    NavigationUI.onNavDestinationSelected(it as MenuItem,navController)
//                }
//                R.id.searchFragment->{
//                    NavigationUI.onNavDestinationSelected(it as MenuItem,navController)
//                }
//                R.id.favorateFragment->{
//                    NavigationUI.onNavDestinationSelected(it as MenuItem,navController)
//                }
//                R.id.accountFragment->{
//                    NavigationUI.onNavDestinationSelected(it as MenuItem,navController)
//                }
//            }
//        }


    }

    override fun onNetworkConnected() {
        Log.e("networktest", "onNetworkConnected:  Network Connected", )

        Toast.makeText(this@MainActivity, "internet connected!!", Toast.LENGTH_SHORT).show()
    }
    override fun onNetworkDisconnected() {
        Toast.makeText(this@MainActivity, "internet disconnected!!", Toast.LENGTH_SHORT).show()
    }
}
