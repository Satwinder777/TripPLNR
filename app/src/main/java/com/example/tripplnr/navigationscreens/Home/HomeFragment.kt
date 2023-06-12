package com.example.tripplnr.navigationscreens.Home


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHomeBinding
import com.example.tripplnr.navigationscreens.Home.adapter.HotelRecyclerViewAdapter
import com.example.tripplnr.navigationscreens.Home.adapter.TravelBlogAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.Home.hotel.HotelFragment
import com.example.tripplnr.navigationscreens.Search.SearchFragment
import com.example.tripplnr.navigationscreens.ViewModel.HomeViewModel
import com.example.tripplnr.navigationscreens.objectfun.mLive
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.coroutines.*

class HomeFragment : Fragment(), TravelBlogAdapter.onItemClick  {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rcTravelBlog: RecyclerView
    private lateinit var popularHotelRc: RecyclerView
    private lateinit var adapter: TravelBlogAdapter
    private lateinit var viewmodelhome: HomeViewModel


//    private val viewModelFavorate by viewModels<FavorateViewModel>()


//    private var viewModel : MyViewModel by viewModels()
//    private val viewModel: MyViewModel by viewModels()
//    val viewModelFactory = MyViewModelFactory(myParameter)
//    viewModel = ViewModelProvider(this, viewModelFactory).get(MyViewModel::class.java)
//    val viewmodelfactory = ViewModelFactory(TripRepository(Massage("satwinderSherGillk")),null)
//    val favorateFactory = ViewModelFactory(null,favorateList)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        Log.e("testqwer", "  test oncreate ",)

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
        binding.viewHotelCard.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)

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


            val newFragment = SearchFragment(1)
//            val targetFragment = TargetFragment()
            val fragmentManager = requireParentFragment().parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()



            // Set up the adapter and associate with the TabLayout
//            val viewAdapter = ViewPagerAdapter(requireContext(),childFragmentManager,lifecycle)
//            vp?.adapter = viewAdapter
//            tl?.setupWithViewPager(vp)

            // Set the desired tab position in the TabLayout and ViewPager
//                vp?.currentItem = 1
//            tl?.setupWithViewPager(vp)


        }
        GlobalScope.launch(Dispatchers.Main){
            binding.shimmerFrameLayout.startShimmer()
            delay(4000)
            binding.shimmerFrameLayout.stopShimmer()
            binding.rcTravelBlog.visibility = View.VISIBLE
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.popularHotelRc.visibility = View.VISIBLE


        }

    }

    override fun onclickItem(position: Int) {

        val newFragment = HotelFragment()
//            val targetFragment = TargetFragment()
        val fragmentManager = requireParentFragment().parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()

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






}



