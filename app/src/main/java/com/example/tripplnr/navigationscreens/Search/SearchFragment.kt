package com.example.tripplnr.navigationscreens.Search

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.Transliterator.Position
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentSearchBinding
import com.example.tripplnr.navigationscreens.Account.AccountFragment
import com.example.tripplnr.navigationscreens.Home.HomeFragment
import com.example.tripplnr.navigationscreens.Search.adapter.ViewPagerAdapter
import com.example.tripplnr.navigationscreens.Search.blog.BlogsFragment
import com.example.tripplnr.navigationscreens.Search.hotel.HotelsFragment
import com.example.tripplnr.navigationscreens.favorateFragment.FavorateFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.DelicateCoroutinesApi


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment(var tabpos:Int?=0) : Fragment() {
    private lateinit var binding :FragmentSearchBinding
    private lateinit var tabLayout :TabLayout
    private lateinit var viewPager :ViewPager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        return binding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged", "SetTextI18n", "InflateParams", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = binding.tabLayout1
        viewPager = binding.viewPager1
        viewPager.adapter = ViewPagerAdapter(requireContext(), childFragmentManager, lifecycle)

        viewPager.currentItem = tabpos!!

        tabLayout.setupWithViewPager(viewPager)
        binding.searchBackCard.setOnClickListener {
//                    requireParentFragment().requireActivity().onBackPressed()
//                 requireActivity().supportFragmentManager.popBackStack()


// Get the reference to the FragmentManager
            val fragmentManager = requireActivity().supportFragmentManager

// Check the back stack count
            val backStackCount = fragmentManager.backStackEntryCount

// Pop the fragment from the back stack if there are more than one fragments in the stack
            if (backStackCount > 1) {
                fragmentManager.popBackStackImmediate()
            } else {
                // Handle the back navigation as needed, e.g., navigate to a different screen or finish the activity
                requireActivity().onBackPressed()
            }
            Log.e("backcount", "onViewCreated: $backStackCount",)

        }




        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {


//                tabLayout.animationMode = TabLayout.AnimationMode.PADDLE


                val tabIndex = tab?.position ?: return

//                val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
                var hotel =  view.findViewWithTag<Chip>(R.string.chip1)
                var blogs =  view.findViewWithTag<Chip>(R.string.chip2)

                when(tabIndex){
                    0->{
//                        bottomNavigationView.menu.getItem(2).isVisible = false
//                        bottomNavigationView.menu.findItem(R.id.searchFragment).setChecked(true)
//                        hotel.chipBackgroundColor.getColorForState(this,R.color.yellow)
                        hotel.setChipBackgroundColorResource(R.color.white)
                        hotel.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                        blogs.setChipBackgroundColorResource(R.color.yellow1)
                        blogs.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                    }
                    1->{
//                        bottomNavigationView.menu.getItem(2).isVisible = true
////                bottomNavigationView.se
//                        bottomNavigationView.menu.findItem(R.id.blogsFragment).setChecked(true)


                        blogs.setChipBackgroundColorResource(R.color.white)
                        blogs.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

                        hotel.setChipBackgroundColorResource(R.color.yellow1)
                        hotel.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                    }

                }



            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                tab?.customView?.findViewById<Chip>(R.id.tab_chip)?.isChecked = false
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Do nothing
            }
        })

        for (i in 0 until tabLayout.tabCount) {

            when (i) {
                0 -> {
                    val tab0 = LayoutInflater.from(requireContext()).inflate(R.layout.chip, null)
//                    layoutInflater.inflate(R.layout.chip, null)
                    var chip = tab0.findViewById<Chip>(R.id.tab_chip)
                    chip.tag = R.string.chip1
                    chip.text = "Hotels "
                    var tab = tabLayout.getTabAt(0)
//                val chip = layoutInflater.inflate(R.layout.chip, null)

                    tab?.customView = chip

                    (tab?.customView as Chip?)?.setOnClickListener {

                        viewPager.currentItem = 0
                    }
                    chip.setChipBackgroundColorResource(R.color.white)
                    chip.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))

                }
                1 -> {

                    val tab1 = LayoutInflater.from(requireContext()).inflate(R.layout.chip, null)
                    var chip = tab1.findViewById<Chip>(R.id.tab_chip)
//                    var chip = requireView().findViewById<Chip>(R.id.tab_chip)
                    chip.text = "Blogs"
                    chip.tag = R.string.chip2

                    val tabPosition = tabLayout.getTabAt(1)

                    tabPosition?.customView = chip

                    (tabPosition?.customView as Chip?)?.setOnClickListener {
                        viewPager.currentItem = 1

                        Log.e("test24", "onViewCreated: clicked ", )}
                    chip.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))

                }
            }
        }

        setbg()
}


//     fun changeTab(position: Int){
//         tabPosition = position
////        var vp =  view?.findViewById<ViewPager>(R.id.viewPager1)
////        var tl =  view?.findViewById<TabLayout>(R.id.tabLayout1)
////        vp?.currentItem = position
////        tl?.setupWithViewPager(vp)
//         Log.e("tabpos", "changeTab: $position", )
//    }


    private fun setbg(){
        var hotel =  view?.findViewWithTag<Chip>(R.string.chip1)
        var blogs =  view?.findViewWithTag<Chip>(R.string.chip2)
        if (tabLayout.selectedTabPosition==0){
            hotel?.setChipBackgroundColorResource(R.color.white)
            hotel?.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            blogs?.setChipBackgroundColorResource(R.color.yellow1)
            blogs?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
        else{
            blogs?.setChipBackgroundColorResource(R.color.white)
            blogs?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            hotel?.setChipBackgroundColorResource(R.color.yellow1)
            hotel?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }

    }
//    fun initchipcolor(){
//        var hotelchip = view?.findViewWithTag<Chip>("chip1")
//        var blogchip = view?.findViewWithTag<Chip>("chip2")
//
//        hotelchip.setChipBackgroundColorResource()
//    }
}




//val button = view.findViewById<Button>(R.id.button)
//button.setOnClickListener {
//
//}