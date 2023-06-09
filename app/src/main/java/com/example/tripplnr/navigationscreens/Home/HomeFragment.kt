package com.example.tripplnr.navigationscreens.Home


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHomeBinding
import com.example.tripplnr.navigationscreens.Home.adapter.HotelRecyclerViewAdapter
import com.example.tripplnr.navigationscreens.Home.adapter.TravelBlogAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.Home.hotel.HotelFragment
import com.example.tripplnr.navigationscreens.Search.SearchFragment
import com.example.tripplnr.navigationscreens.ViewModel.HomeViewModel
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.example.tripplnr.navigationscreens.objectfun.mLive
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.*
import java.lang.reflect.Constructor
import java.util.*

class HomeFragment : Fragment(), TravelBlogAdapter.onItemClick  {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rcTravelBlog: RecyclerView
    private lateinit var popularHotelRc: RecyclerView
    private lateinit var adapter: TravelBlogAdapter
    private lateinit var viewmodelhome: HomeViewModel
    private lateinit var share_phref :SharedPreferences
    private lateinit var editor: SharedPreferences.Editor






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        share_phref = requireContext().getSharedPreferences("home_share_phref",Context.MODE_PRIVATE)
        editor = share_phref.edit()


        return binding.root
    }




    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.P)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcTravelBlog = binding.rcTravelBlog
        popularHotelRc = binding.popularHotelRc

        viewmodelhome = ViewModelProvider(this).get(HomeViewModel::class.java)
//        viewmodelhome.initData()

        val chip: Chip = binding.viewHotelCard as Chip

        val outlineSpotShadowColor = ContextCompat.getColor(requireContext(), R.color.yellow)


        val chipDrawable = chip.chipDrawable as? ChipDrawable
//            chipDrawable?.setSpotShadowColor(outlineSpotShadowColor)
//            chipDrawable?.setShadowColor(outlineSpotShadowColor)
//            chip.outlineSpotShadowColor = outlineSpotShadowColor
        chip.elevation = 900f
        chip.setShadowLayer(0f, 10f, 50f, outlineSpotShadowColor)
        chip.outlineSpotShadowColor = outlineSpotShadowColor

//        SearchFragment()
        binding.viewHotelCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }


        var mt = listOf<travelBlogItem>()

        mt = mLive.data.value!!

       adapter = TravelBlogAdapter(
                          mt,
           this,
                     )
        rcTravelBlog.adapter = adapter
        adapter.notifyDataSetChanged()
//        Log.e("testdata12", "onViewCreated: ${viewmodelhome.itemList  },${viewmodelhome.itemList.value}", )



//        viewmodelhome.hotelData()
        viewmodelhome.hotelData()
       var datarc2 =  viewmodelhome.rc2.value
        val adapter = HotelRecyclerViewAdapter(datarc2!! )
        popularHotelRc.adapter = adapter
        adapter.notifyDataSetChanged()
        binding.SeeAllBlog.setOnClickListener {

//            val tl =  requireParentFragment().requireActivity().requireViewById<TabLayout>(R.id.tabLayout1)
//            viewpagerinstance.currentItem = 1
//            var vp =  sF?.requireActivity()?.findViewById<ViewPager>(R.id.viewPager1)
//            var tl =  sF?.requireActivity()?.findViewById<TabLayout>(R.id.tabLayout1)



            val bundle = Bundle()

            bundle.putInt("index", 1)
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment,bundle)

////            val targetFragment = TargetFragment()
//            val fragmentManager = requireParentFragment().parentFragmentManager
//            val transaction = fragmentManager.beginTransaction()
//            transaction.replace(R.id.nav_host_fragment, newFragment)
//            transaction.addToBackStack("home_to_search")
//            transaction.commit()



            // Set up the adapter and associate with the TabLayout
//            val viewAdapter = ViewPagerAdapter(requireContext(),childFragmentManager,lifecycle)
//            vp?.adapter = viewAdapter
//            tl?.setupWithViewPager(vp)

            // Set the desired tab position in the TabLayout and ViewPager
//                vp?.currentItem = 1
//            tl?.setupWithViewPager(vp)


        }
//        if (viewmodelhome.showShimmer==true){
//            binding.shimmerFrameLayout.startShimmer()
//            viewmodelhome.showShimmer = false
//        }
//        else{
//            Toast.makeText(requireContext(), "shimmering offfff", Toast.LENGTH_SHORT).show()
//            binding.shimmerFrameLayout.stopShimmer()
//            binding.shimmerFrameLayout.visibility = View.GONE
//        }

        binding.apply {

            viewmodelhome.apply {

                when(shimmering.value){
                    null->{GlobalScope.launch(Dispatchers.Main) { simmering() }}
                    true->{

                        Log.e("s_simmer", "onViewCreated: true ", )
                    }
                    false->{
                        notshimmer()
                        Log.e("s_simmer", "onViewCreated: false ", )

                    }

                    else -> {
                        Log.e("s_simmer", "onViewCreated else: ${shimmering.value}", )
                    }
                }

            }

        }

    }

    override fun onPause() {
        super.onPause()

        viewmodelhome.stopShimmer()
        Log.e("s_simmer", "onPause: pause${viewmodelhome.shimmering.value}", )



    }

    override fun onResume() {
        super.onResume()

        viewmodelhome.startShimmer()



    }



    override fun onclickItem(position: Int, placetext: String?) {
//        val bundle = Bundle()
////                args.putParcelable("guest", guestLiveData.value)
////                args.putString("date",dateLiveData.value)
//        bundle.putString("place", placetext)
//
//        val newFragment = HotelFragment()
//        newFragment.arguments = bundle
////            val targetFragment = TargetFragment()
//        val fragmentManager = requireParentFragment().parentFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        transaction.replace(R.id.nav_host_fragment, newFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()


    }




//    override fun onfavoratebtnClicks(position: Int) {
////        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
////        bottomNavigationView.menu.getItem(2).isVisible = true
//        //        findNavController().navigate(R.id.favorateFragment)
//
//
////        bottomNavigationView.menu.findItem(R.id.blogsFragment).setChecked(true)
////        val newFragment = FavorateFragment()
//////            val targetFragment = TargetFragment()
////        val fragmentManager = requireParentFragment().parentFragmentManager
////        val transaction = fragmentManager.beginTransaction()
////        transaction.replace(R.id.nav_host_fragment, newFragment)
////        transaction.addToBackStack(null)
////        transaction.commit()
//
//    }





    private fun isDarkModeEnabled(): Boolean {
        val nightModeFlags = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES
    }

private fun testcheck(){
//    if( ){
//
//    }
}
//    interface m_index{
//        fun setIndexing()
//    }

    @OptIn(DelicateCoroutinesApi::class)
    fun notshimmer() {


            binding.shimmerFrameLayout.stopShimmer()
            binding.rcTravelBlog.visibility = View.VISIBLE
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.popularHotelRc.visibility = View.VISIBLE

    }
    suspend fun simmering() {
        // Simulating a long-running task
        binding.shimmerFrameLayout.startShimmer()
        delay(1500)
        binding.shimmerFrameLayout.stopShimmer()
        binding.rcTravelBlog.visibility = View.VISIBLE
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.popularHotelRc.visibility = View.VISIBLE


    }

}



