package com.example.tripplnr

import android.app.ProgressDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tripplnr.databinding.ActivityMainBinding
import com.example.tripplnr.navigationscreens.Home.NetworkConnectivityListener
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



        supportActionBar?.setBackgroundDrawable(null)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

       navController = navHostFragment.navController

        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setBackgroundResource(R.drawable.bg_botton_nav)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)



         // Replace with the ID of your button
        var btn  = bottomNavigationView.findViewById<View>(R.id.homeFragment)
        var btn1  = bottomNavigationView.findViewById<View>(R.id.searchFragment)
        navigateScreen(btn,R.id.homeFragment)
//        navigateScreen(btn1, R.id.searchFragment)

//        bottomNavigationView.setOnClickListener {

            }
//        }




    override fun onNetworkConnected() {
        Log.e("networktest", "onNetworkConnected:  Network Connected", )

        Toast.makeText(this@MainActivity, "internet connected!!", Toast.LENGTH_SHORT).show()
    }
    override fun onNetworkDisconnected() {
        Toast.makeText(this@MainActivity, "internet disconnected!!", Toast.LENGTH_SHORT).show()
    }
    private fun navigateScreen(view: View, fragment: Int){
        view.setOnClickListener {
            navController.navigateUp()
//            navController.navigate(fragment)
        }
    }
}
