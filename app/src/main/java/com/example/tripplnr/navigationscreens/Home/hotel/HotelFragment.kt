package com.example.tripplnr.navigationscreens.Home.hotel

import android.app.ActionBar.OnNavigationListener
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHotelBinding
import com.example.tripplnr.navigationscreens.Home.hotel.adapter.PageAdapter1
import com.google.android.material.tabs.TabLayout


class HotelFragment : Fragment() {
    private lateinit var binding :FragmentHotelBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHotelBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPager

        viewPager.adapter = PageAdapter1(requireContext(),childFragmentManager,lifecycle)

        val tabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)


        sliderImageSet()

        binding.backbtnhotlFragment.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
//        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
////                TODO("Not yet implemented")
//                tab?.position
//                tabLayout.(R.color.yellow,R.color.yellow)
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
//            }
//
//        })

        binding.shareBtn.setOnClickListener {
            val textToShare = "Hello, this is the content to be shared."
            val subject = "Shared Content"

            shareContent(textToShare, subject, requireContext())

        }

    }
    private fun sliderImageSet() {
        val imageList = ArrayList<SlideModel>()
        var a = "android.resource://" + requireContext().packageName + "/" + R.drawable.img1
        var b = "android.resource://" + requireContext().packageName + "/" + R.drawable.img2
        var c = "android.resource://" + requireContext().packageName + "/" + R.drawable.im3
        var a1 = Uri.parse(a)
        var a2 = Uri.parse(b)
        var a3 = Uri.parse(c)
        imageList.add(SlideModel(a, "", ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(b,"" , ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(c, "", ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel("https://bit.ly/2BteuF2","" , ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel("https://bit.ly/2YoJ77H", "", ScaleTypes.CENTER_CROP))

        binding.imageSlider.setImageList(imageList)

    }

    fun shareContent(text: String, subject: String, context: Context) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)

        val shareIntent = Intent.createChooser(intent, "Share via")
        context.startActivity(shareIntent)
    }

//    @Deprecated("Deprecated in Java")
//    override fun onNavigationItemSelected(itemPosition: Int, itemId: Long): Boolean {
//        findNavController().navigateUp()
//        findNavController().navigate(R.id.searchFragment)
//        return true
//    }
}