package com.example.tripplnr.navigationscreens.favorateFragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentFavorateBinding
import com.example.tripplnr.navigationscreens.Account.activity.CreateUserActivity
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.ViewModel.HomeViewModel
import com.example.tripplnr.navigationscreens.favorateFragment.Adapter.FavorateFragmentAdapter
import com.google.android.material.button.MaterialButton

class FavorateFragment : Fragment(), FavorateFragmentAdapter.onItemClick {
    private lateinit var binding: FragmentFavorateBinding
    private lateinit var rc: RecyclerView
    var TAG = "test23"
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    lateinit var homeViewModel: HomeViewModel
//    val myRepository = HomeFragment().favorateList // Provide your repository instance

    private lateinit var adapter: FavorateFragmentAdapter
//    val viewModelpr = ViewModelProvider(this, viewModelFactory)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentFavorateBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val finalviewmodel = ViewModelProvider(this,viewModelpr).get(FavorateViewModel::class.java)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.initLiveData(viewLifecycleOwner)


        rc = binding.favorateRecyclerView

        rc.layoutManager = LinearLayoutManager(requireContext())

        adapter = FavorateFragmentAdapter(
            this,
            requireContext(),
            homeViewModel.favoriteItems
        )



        rc.adapter = adapter

        homeViewModel.favoriteItems.value?.forEach { Log.e(TAG, "onViewCreated:  ${it.placetextuser}", ) }
        Log.e(
            "datacheckkkkkk",
            "onCreateViewHolder: $${homeViewModel.favoriteItems.value?.size},  : "
        )

        binding.backbtnFavorateFragment.setOnClickListener {
//            childFragmentManager.popBackStack()
            requireParentFragment().requireActivity().onBackPressed()
//            parentFragmentManager.popBackStack()
//            val bottomNavigationView =
//                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
//            bottomNavigationView.menu.getItem(2).isVisible = false
//            findNavController().navigateUp()
        }


    }

    override fun onclickItem(position: Int) {

    }

    override fun onfavoratebtnClicks(position: Int) {
        TODO("Not yet implemented")
    }

    override fun showtext(position: Int) {

        popupFavo()

    }

    override fun dltBlog(position: Int?, itemposition: travelBlogItem?) {

        when(position){
            null->{
                Log.e(TAG, "dltBlog: null", )
            }
            else-> {
                homeViewModel.favoriteItems.value?.removeAt(position)

                adapter.notifyItemRemoved(position)
                adapter.notifyDataSetChanged()

//              homeViewModel.favoriteItems.value?.forEach{
//                   it->
//                   if (itemposition==it){
//                       it.isfavorate = false
//                   }


               }
            }
        }
//

           
//            val indexinhome = homeViewModel.favoriteItems.value!!.indexOf(travelBlogItem())
//        Log.e(TAG, "dltBlog: $indexinhome", )
//            if (homeViewModel.favoriteItems.value?.contains(itemposition) == true){
//                homeViewModel.favoriteItems.value!![indexinhome].isfavorate = false
//                itemposition?.isfavorate = false
//            homeViewModel.favoriteItems.value!!.get(indexinhome).isfavorate = false
////            }
            
//        }
//        catch (e:ArrayIndexOutOfBoundsException){
//            adapter.", )
//        }




    @SuppressLint("MissingInflatedId", "InflateParams")
    private fun popupFavo(){
        var view = layoutInflater.inflate(R.layout.favorate_popup,null,false)

        var pop = PopupWindow(view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true )

//            pop.contentView = view
        pop.showAtLocation(view, Gravity.CENTER,0,0)
        var closebtn = view.findViewById<Button>(R.id.LoginButton)
        closebtn.setOnClickListener {
            pop.dismiss()
            loginpop()
        }

    }

    @SuppressLint("MissingInflatedId", "InflateParams")
    private fun loginpop(){
        var view = layoutInflater.inflate(R.layout.login_display,null,false)

        var pop = PopupWindow(view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true )

//            pop.contentView = view
        pop.showAtLocation(view,Gravity.CENTER,0,0)
        var closebtn = view.findViewById<ImageView>(R.id.closeLogin)
        closebtn.setOnClickListener {
            pop.dismiss()
        }

        var loginBtn = view.findViewById<MaterialButton>(R.id.loginMButton)
        loginBtn.setOnClickListener {
            checkLocationPermission()
            pop.dismiss()
        }
        var createAcc =  view.findViewById<TextView>(R.id.createAccount)
        createAcc.setOnClickListener {
            pop.dismiss()
            val intent = Intent(requireContext(), CreateUserActivity::class.java)
            startActivity(intent)
        }

    }


    private fun checkLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val permissionGranted = PackageManager.PERMISSION_GRANTED

        if (ContextCompat.checkSelfPermission(requireContext(), permission) != permissionGranted) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(permission),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission has already been granted
            // Perform your location-related tasks here
            Toast.makeText(requireContext(), "already Exist!", Toast.LENGTH_SHORT).show()



        }
    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission has been granted
                // Perform your location-related tasks here
                Toast.makeText(requireContext(), "Permission Granted!", Toast.LENGTH_SHORT).show()
            } else {
                // Location permission has been denied
                // Handle the denial or disable location-related functionality
                Toast.makeText(requireContext(), "Failed to Access Permission!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
