package com.example.tripplnr.navigationscreens.Search

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentSearchBinding
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Search.adapter.RecyclerAdapterSearchFr
import com.example.tripplnr.navigationscreens.Search.adapter.ViewPagerAdapter
import com.example.tripplnr.navigationscreens.favorateFragment.FavorateFragment
import com.example.tripplnr.navigationscreens.hotelListFragment.HotelListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private lateinit var binding :FragmentSearchBinding


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

        var tabLayout = binding.tabLayout
        var viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(requireContext(),childFragmentManager,lifecycle)

        viewPager.currentItem = 0
        tabLayout.setupWithViewPager(viewPager)


        binding.searchBackCard.setOnClickListener {

            findNavController().navigate(R.id.homeFragment)






        }







        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                val tabIndex = tab?.position ?: return

                val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
                var hotel =  view.findViewWithTag<Chip>(R.string.chip1)
                var blogs =  view.findViewWithTag<Chip>(R.string.chip2)

                when(tabIndex){
                    0->{
//                        bottomNavigationView.menu.getItem(2).isVisible = false
//                        bottomNavigationView.menu.findItem(R.id.searchFragment).setChecked(true)
//                        hotel.chipBackgroundColor.getColorForState(this,R.color.yellow)
                        hotel.setChipBackgroundColorResource(R.color.yellow)
                        hotel.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                        blogs.setChipBackgroundColorResource(R.color.light_yellow)
                        blogs.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

                    }
                    1->{
//                        bottomNavigationView.menu.getItem(2).isVisible = true
////                bottomNavigationView.se
//                        bottomNavigationView.menu.findItem(R.id.blogsFragment).setChecked(true)


                        blogs.setChipBackgroundColorResource(R.color.yellow)
                        blogs.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                        hotel.setChipBackgroundColorResource(R.color.light_yellow)
                        hotel.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

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
                    val tab1 = layoutInflater.inflate(R.layout.chip, null)

                    var chip = tab1.findViewById<Chip>(R.id.tab_chip)
                    chip.tag = R.string.chip1
                    chip.text = "Hotels "
                    var tab = tabLayout.getTabAt(0)
//                val chip = layoutInflater.inflate(R.layout.chip, null)
                    tab?.customView = chip


                }
                1 -> {

                    val tab1 = layoutInflater.inflate(R.layout.chip, null)

                    var chip = tab1.findViewById<Chip>(R.id.tab_chip)
                    chip.text = "Blogs"
                    chip.tag = R.string.chip2

                    var tab = tabLayout.getTabAt(1)

                    tab?.customView = chip

                }
            }
        }


//        FavorateFragment.myObject.doSomething(requireActivity().findViewById(R.id.bottom_navigation))


    }
}




//val button = view.findViewById<Button>(R.id.button)
//button.setOnClickListener {
//
//}