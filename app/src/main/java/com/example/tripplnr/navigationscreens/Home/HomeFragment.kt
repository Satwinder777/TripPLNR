package com.example.tripplnr.navigationscreens.Home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHomeBinding
import com.example.tripplnr.navigationscreens.Home.adapter.HotelRecyclerViewAdapter
import com.example.tripplnr.navigationscreens.Home.adapter.TravelBlogAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.homeItem
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Home.hotel.HotelFragment
import com.example.tripplnr.navigationscreens.Search.SearchFragment
import com.example.tripplnr.navigationscreens.favorateFragment.FavorateFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), TravelBlogAdapter.onItemClick {
    private lateinit var binding :FragmentHomeBinding
    private lateinit var rcTravelBlog :RecyclerView
    private lateinit var popularHotelRc :RecyclerView
    private lateinit var adapter :TravelBlogAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcTravelBlog = binding.rcTravelBlog
        popularHotelRc = binding.popularHotelRc

            adapter =  TravelBlogAdapter(datahandle(),this)
            rcTravelBlog.adapter = adapter

        GlobalScope.launch {
            val adapter = HotelRecyclerViewAdapter(hotelData())
            popularHotelRc.adapter = adapter
        }

        binding.viewHotelCard.setOnClickListener {
            findNavController().navigate(R.id.searchFragment )
        }
//            FavorateFragment.myObject.doSomething(requireActivity().findViewById(R.id.bottom_navigation))


    }
    fun datahandle():List<homeItem>{
        var list  = listOf<homeItem>(homeItem(R.drawable.explore2,"the Golden Temple","12 may 23 ","1.32s",getString(R.string.testLine)),

            homeItem(R.drawable.exploreimg,"the Royal Temple","12 may 23 ","1.35s",getString(R.string.testLine)),
            homeItem(R.drawable.exploreimg,"the Swanrana mandhir ","12 may 23 ","1.11s",getString(R.string.testLine)),
            homeItem(R.drawable.explore2,"the love city","12 may 23 ","12.32s",R.string.testLine.toString()),
            homeItem(R.drawable.exploreimg,"the Punjaab","12 may 23 ","59.32s",R.string.testLine.toString()),

        )
        return list
    }

    fun hotelData():List<hotelTitle>{
        val hotelchildData  = listOf<hotelchild>(
            hotelchild(R.drawable.explore2,"Taj Hotel","Amritsar","3.3"),
            hotelchild(R.drawable.exploreimg,"The Bill Gates","America,Us","4.5"),
            hotelchild(R.drawable.explore2,"Punjab Hotel","Amritsar,Punjab","5.9"),
            hotelchild(R.drawable.exploreimg,"Chandighar Hotel","Chandighar, India","4.7"),
            hotelchild(R.drawable.explore2,"Us Hotel","Us,Amercia","4.8"),
            )
        var list  = listOf<hotelTitle>(
            hotelTitle("Top Hotel",hotelchildData),
            hotelTitle("Best Hotel",hotelchildData),
            hotelTitle("Old Hotel",hotelchildData),
            hotelTitle("Gold Hotel",hotelchildData),

            )
        return list
    }

    override fun onclickItem(position: Int) {
//        val fragmentManager = supportFragmentManager // For activities
// OR
        val newFragment = HotelFragment()
//            val targetFragment = TargetFragment()
        val fragmentManager = requireParentFragment().parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()


    }

    override fun onfavoratebtnClicks(position: Int) {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.menu.getItem(2).isVisible = true


//        bottomNavigationView.menu.findItem(R.id.blogsFragment).setChecked(true)
//        val newFragment = FavorateFragment()
////            val targetFragment = TargetFragment()
//        val fragmentManager = requireParentFragment().parentFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        transaction.replace(R.id.nav_host_fragment, newFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
        findNavController().navigate(R.id.favorateFragment)

    }

    override fun showtext(position: Int) {

    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun showtext(position: Int) {
////        adapter.data
//    }


}
