package com.example.tripplnr.navigationscreens.Home.hotel.adapter

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.DealsFragment
import com.example.tripplnr.navigationscreens.Home.hotel.fragments.Details.DetailsFragment
import com.example.tripplnr.navigationscreens.Home.hotel.fragments.Reviews.ReviewFragment


class PageAdapter1(var context: Context, fm:FragmentManager,lifecycle:Lifecycle): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return DealsFragment()
            }
            1 -> {
                return DetailsFragment()
            }

            2 -> {
                return ReviewFragment()
            }
            else -> {
                Toast.makeText(context, "Something went Wrong!", Toast.LENGTH_SHORT).show()
                return ReviewFragment()
            }
        }


    }

    override fun getPageTitle(position: Int): CharSequence? {
//        return super.getPageTitle(position)
        when(position){
            0->{

                return "Deals"
            }
            1->{
                return "Details"

            }
            2->{
                return "Reviews"

            }
            else->{
                return super.getPageTitle(position)
            }
        }
    }

}