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
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentFavorateBinding
import com.example.tripplnr.navigationscreens.Account.activity.CreateUserActivity
import com.example.tripplnr.navigationscreens.Home.HomeFragment
import com.example.tripplnr.navigationscreens.Home.adapter.TravelBlogAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.LiveDataVM.Live
import com.example.tripplnr.navigationscreens.ViewModel.ViewModelFactory
import com.example.tripplnr.navigationscreens.favorateFragment.Adapter.FavorateFragmentAdapter
import com.example.tripplnr.navigationscreens.favorateFragment.ViewModel.FavorateViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class FavorateFragment : Fragment(), FavorateFragmentAdapter.onItemClick {
    private lateinit var binding: FragmentFavorateBinding
    private lateinit var rc: RecyclerView
    var TAG = "test23"
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
      var viewModelFavorate = viewModels<FavorateViewModel>()
    val myRepository = HomeFragment().favorateList // Provide your repository instance
    val viewModelpr = ViewModelFactory(null,myRepository)
    private lateinit var adapter : FavorateFragmentAdapter
//    val viewModelpr = ViewModelProvider(this, viewModelFactory)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val finalviewmodel = ViewModelProvider(this,viewModelpr).get(FavorateViewModel::class.java)

        binding = FragmentFavorateBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val finalviewmodel = ViewModelProvider(this,viewModelpr).get(FavorateViewModel::class.java)




        rc = binding.favorateRecyclerView

        rc.layoutManager = LinearLayoutManager(requireContext())

        adapter =  FavorateFragmentAdapter( this, Live.data.value,requireContext())
        Log.e(TAG, "onViewCreated: ${Live.data1}", )
        rc.adapter = adapter

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

//    fun datahandle(): MutableList<travelBlogItem> {
//        var list = mutableListOf<travelBlogItem>(
//            travelBlogItem(
//                R.drawable.explore2,
//                "the Golden Temple",
//                "12 may 23 ",
//                "1.32s",
//                getString(R.string.testLine)
//            ),
//
//            travelBlogItem(
//                R.drawable.exploreimg,
//                "the Royal Temple",
//                "12 may 23 ",
//                "1.35s",
//                getString(R.string.testLine)
//            ),
//            travelBlogItem(
//                R.drawable.exploreimg,
//                "the Swanrana mandhir ",
//                "12 may 23 ",
//                "1.11s",
//                getString(R.string.testLine)
//            ),
//            travelBlogItem(
//                R.drawable.explore2,
//                "the love city",
//                "12 may 23 ",
//                "12.32s",
//                R.string.testLine.toString()
//            ),
//            travelBlogItem(
//                R.drawable.exploreimg,
//                "the Punjaab",
//                "12 may 23 ",
//                "59.32s",
//                R.string.testLine.toString()
//            ),
//
//            )
//        return list
//    }

    override fun onclickItem(position: Int) {

    }

    override fun onfavoratebtnClicks(position: Int) {
        TODO("Not yet implemented")
    }

    override fun showtext(position: Int) {

        popupFavo()

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun dltBlog(position: Int?) {

        if (position != null) {
            Live.data1?.removeAt(position)
//            Live.data1.let { item->
//                val currentItem = item?.get(position)
//                currentItem?.isfavorate = false
//            }
            Live.data.observe(viewLifecycleOwner, Observer {
                it[position].isfavorate = false
            })
            adapter.notifyItemRemoved(position)
        }


        adapter.notifyDataSetChanged()
        Log.e(TAG, "dltBlog: ${Live.data1}", )
    }

//override fun onDestroyView() {
//    super.onDestroyView()
//    val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
//    bottomNavigationView.menu.getItem(2).isVisible = false
//}


//    override fun onclickItem(position: Int) {
//       try{
//
//       }
//       catch (e:Exception){
//           Log.e(TAG, "onclickItem: ${e.message}", )
//       }
//    }
//
//    override fun onfavoratebtnClicks(position: Int) {
////        Log.e(TAG, "onclickItem: testing2" )
//
//    }

//    override fun showtext(position: Int) {
////        Log.e(TAG, "onclickItem: testing3" )
//
//    }

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
