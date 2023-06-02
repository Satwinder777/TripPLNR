package com.example.tripplnr.navigationscreens.Home


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHomeBinding
import com.example.tripplnr.navigationscreens.Home.adapter.HotelRecyclerViewAdapter
import com.example.tripplnr.navigationscreens.Home.adapter.TravelBlogAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.Home.hotel.HotelFragment
import com.example.tripplnr.navigationscreens.LiveDataVM.Live
import com.example.tripplnr.navigationscreens.ViewModel.HomeViewModel
import com.example.tripplnr.navigationscreens.favorateFragment.ViewModel.FavorateViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable

class HomeFragment : Fragment(), TravelBlogAdapter.onItemClick {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rcTravelBlog: RecyclerView
    private lateinit var popularHotelRc: RecyclerView
    private lateinit var adapter: TravelBlogAdapter
    private lateinit var viewmodelhome: HomeViewModel


    private val viewModelFavorate by viewModels<FavorateViewModel>()


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

    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.P)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcTravelBlog = binding.rcTravelBlog
        popularHotelRc = binding.popularHotelRc

        viewmodelhome = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewmodelhome.initLiveData(viewLifecycleOwner)

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

            viewmodelhome.favoriteItems.observe(viewLifecycleOwner, Observer { items ->
                val adapter = TravelBlogAdapter(items, this,
                    items , this)


                rcTravelBlog.adapter = adapter
                adapter.notifyDataSetChanged()
            })





        viewmodelhome.hotelData()
        viewmodelhome.rc2List().observe(viewLifecycleOwner, Observer { items ->
            val adapter = HotelRecyclerViewAdapter(items)
            popularHotelRc.adapter = adapter
            adapter.notifyDataSetChanged()


        })

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

    override fun onfavoratebtnClicks(position: Int) {
//        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNavigationView.menu.getItem(2).isVisible = true
        //        findNavController().navigate(R.id.favorateFragment)


//        bottomNavigationView.menu.findItem(R.id.blogsFragment).setChecked(true)
//        val newFragment = FavorateFragment()
////            val targetFragment = TargetFragment()
//        val fragmentManager = requireParentFragment().parentFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        transaction.replace(R.id.nav_host_fragment, newFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()

    }

    override fun addOrDlt(
        like: Boolean,
        position: Int,
        favorateBtn: ImageView,
        blog: travelBlogItem
    ) {
        if (like == true) {
            Live.data1?.remove(blog)
        } else {
            Live.data1?.add(blog)
        }
    }


    override fun addifFAv(
        itemposition: travelBlogItem,
        favorateBtn: ImageView,
        currentblog: travelBlogItem,
        position: Int,
        list: MutableList<travelBlogItem>
    ) {

    }

    override fun addblogtofavorate( blogItem: travelBlogItem) {
        viewmodelhome.favoriteItems.value?.add(blogItem)
    }

    override fun removeblogfromfavo(blogItem1: Boolean, blogItem: travelBlogItem) {
        Live.data1?.add(blogItem)
        viewmodelhome.favoriteItems.value?.remove(blogItem)
    }

}



