package com.example.tripplnr.navigationscreens.Home.hotel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHotelBinding
import com.example.tripplnr.navigationscreens.Home.hotel.adapter.PageAdapter1
import com.example.tripplnr.navigationscreens.objectfun.Allfun


class HotelFragment : Fragment() {
    private lateinit var binding :FragmentHotelBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHotelBinding.inflate(layoutInflater)
        val placetext = arguments?.getString("place","default" ) ?: ""
        val cityLoc = arguments?.getString("city","default" ) ?: ""
        val rate = arguments?.getFloat("rate",0f ) ?: ""
        binding.hotelNametxt.setText(placetext)
        binding.mHoteltxt.setText(placetext)
        binding.locationTxt.setText(cityLoc)
        binding.ratingBar2.rating = rate as Float
        binding.ratingBar2.setIsIndicator(true)



        return binding.root
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPager

        viewPager.adapter = PageAdapter1(requireContext(),childFragmentManager,lifecycle)

        val tabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)


        sliderImageSet()

        binding.backbtnhotlFragment.setOnClickListener {
//            parentFragmentManager.popBackStack()
//            val fragmentManager = parentFragmentManager
//            fragmentManager.popBackStack("hotel_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            val fragmentManager = requireFragmentManager()

                fragmentManager.popBackStack("hotel_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)



//            val fragmentManager = requireActivity().supportFragmentManager
//
//// Check the back stack count
//            val backStackCount = fragmentManager.backStackEntryCount
//            Log.e("backcount0", "onViewCreated: $backStackCount",)
//// Pop the fragment from the back stack if there are more than one fragments in the stack
//            if (backStackCount > 1) {
//                fragmentManager.popBackStackImmediate()
//            } else {
//                // Handle the back navigation as needed, e.g., navigate to a different screen or finish the activity
//                requireActivity().onBackPressed()
//            }
//            Log.e("backcount", "onViewCreated: $backStackCount",)
        }


        binding.shareBtn.setOnClickListener {
            val textToShare = "https://drive.google.com/file/d/14j3rPhsRFvsK4S0OXlez-Ansl3b7KTsz/view?usp=drive_link"    // 3 jul
            val subject = "Shared Content"

            shareContent(textToShare, subject, requireContext())

        }
        binding.datepickerdate1.setText(Allfun.dateLiveData.value)
        var dateDAta: String? = Allfun.dateLiveData.value



        val guestdetails = Allfun.guestLiveData.value
        val guest = guestdetails?.guest
        val rooms = guestdetails?.rooms
        binding.guestcount1.setText("$guest Guest, $rooms Room")


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