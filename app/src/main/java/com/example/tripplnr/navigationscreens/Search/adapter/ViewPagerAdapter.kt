package com.example.tripplnr.navigationscreens.Search.adapter



import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tripplnr.R

import com.example.tripplnr.navigationscreens.Search.blog.BlogsFragment
import com.example.tripplnr.navigationscreens.Search.hotel.HotelsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class ViewPagerAdapter(var context: Context, fm:FragmentManager,lifecycle:Lifecycle): FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

//    var bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)


    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return HotelsFragment()
            }
            1 -> {
                return BlogsFragment()
            }

            else -> {
                Toast.makeText(context, "Something went Wrong!", Toast.LENGTH_SHORT).show()
                return HotelsFragment()
            }
        }


    }


//    override fun getPageTitle(position: Int): CharSequence? {
////        return super.getPageTitle(position)
//        when(position){
//            0->{
//                return "Posts"
//            }
//            1->{
//                return "Reels"
//
//            }
//            2->{
//                return "TagedPosts"
//
//            }
//            else->{
//                return super.getPageTitle(position)
//            }
//        }
//    }

}