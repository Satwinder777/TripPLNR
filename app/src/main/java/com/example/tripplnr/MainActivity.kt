package com.example.tripplnr

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tripplnr.databinding.ActivityMainBinding
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
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


        if(Allfun.isInternetAvailable(this)==true.not()){



                progressDialog.show()



        }
        else{
            fun dismiss() {
                progressDialog.dismiss()
            }
        }


        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        var navController = navHostFragment.navController

        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setBackgroundResource(R.drawable.bg_botton_nav)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
//        FirebaseApp.initializeApp(this)


//        val selectedColor = ColorStateList.valueOf(R.color.yellow)
//        bottomNavigationView.itemIconTintList = selectedColor // Set icon color to yellow
//        bottomNavigationView.itemTextColor = selectedColor



    }
}
