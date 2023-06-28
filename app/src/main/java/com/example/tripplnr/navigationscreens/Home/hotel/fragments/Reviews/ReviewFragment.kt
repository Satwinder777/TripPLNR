package com.example.tripplnr.navigationscreens.Home.hotel.fragments.Reviews

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.databinding.FragmentReviewBinding
import com.example.tripplnr.navigationscreens.Home.dataclass.RatingData
import com.example.tripplnr.navigationscreens.Home.dataclass.reviewData
import com.example.tripplnr.navigationscreens.Home.dataclass.similarHotel
import com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.DealsFragment
import com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.adapter.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReviewFragment : Fragment() {
    private lateinit var binding : FragmentReviewBinding

    lateinit var ratingrc : RecyclerView

    lateinit var reviewrc : RecyclerView
    lateinit var similarrc : RecyclerView
    lateinit var reviewAdapter : ReviewAdapter
    var isLessReview:Boolean = true
    lateinit var seemore:TextView





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewBinding.inflate(layoutInflater)

        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ratingrc = binding.ratingRecyclerView
        GlobalScope.launch {
            val adapter = RaingHotelRecyclerAdapter(RatingData1())
            ratingrc.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        reviewrc = binding.reviewRecyclerView


            // Assuming you have a larger list of items


// Create a smaller list containing a subset of items


            var lessitem = ReviewData1().take(6)
            reviewAdapter = ReviewAdapter(lessitem,requireContext())
            reviewrc.adapter = reviewAdapter
            reviewAdapter.notifyDataSetChanged()


        similarrc = binding.similarHotelRecyclerview
        GlobalScope.launch {
            val adapter = SimilarHotelAdapter(SimilarData1())
            similarrc.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        GlobalScope.launch {
            onclickreadmore(binding.seemore1,reviewAdapter)
        }
    }



    private fun RatingData1():List<RatingData>{
        var list = listOf<RatingData>(
            RatingData("Location", 10.0),
            RatingData("Room", 10.0),
            RatingData("Breakfast", 6.7),
            RatingData("Clean", 6.5),
            RatingData("Bed", 3.3),
        )
        return list
    }


    private fun ReviewData1():List<reviewData>{
        var list = listOf<reviewData>(
            reviewData("Satwinder Singh","the numberOne on Top Hotel",8.9),
            reviewData("Punjab Singh","the numberOne on Top Hotel",9.34),
            reviewData("Abhushek Shukla","the numberOne on Top Hotel",5.9),
            reviewData("RAman Thakur","the numberOne on Top Hotel",6.94),
            reviewData("Sher Gill ","the numberOne on Top Hotel",8.59),
            reviewData("Banta Singh","the numberOne on Top Hotel",22.3),
            reviewData("Banta Singh","the numberOne on Top Hotelthe numberOne on Top Hotel",22.3),
            reviewData("Santa Singh","the numberOne on Top Hotelthe numberOne on Top Hotelthe numberOne on Top Hotel",3.89),
        )
        return list
    }


    private fun SimilarData1():List<similarHotel>{
        var list = listOf<similarHotel>(
            similarHotel("Hotel Kuber 7","Mohali ,India ",3.9),
            similarHotel("Hotel West End View","Zirakpur ,India ",7.7),
            similarHotel("The Lalit Chandighar","Chandighar, India",7.3),
        )
        return list
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    fun onclickreadmore(view: View, adapter: ReviewAdapter?){

        var seemorerc = binding.seemore1
//        var showmore   = binding.readmoreTextView
//        var showmoreReview =  binding.reviewRec
//        var sizeless :Boolean = true
//        var sizeless1 :Boolean = true
        var isLessreview:Boolean = true
//        var multitext = binding.editTextTextMultiLine
//
//        var ammen = binding.offeringAmmen
//        var ammenofferrc = binding.amenitiesOfferRecycler

        view.setOnClickListener {

            when(view){
//                showmore->{
//                    if(sizeless==true){
//                        multitext.maxLines = Int.MAX_VALUE
//
//                        showmore.setText("Show Less..")
//                        sizeless = false
//
//                    }
//                    else{
//                        multitext.setLines(4)
//                        showmore.setText("Show More..")
//                        sizeless = true
//                    }
//                }
//                newshowmore1->{
//                    if(sizeless1==true){
//
//                        ammen.visibility = View.VISIBLE
//                        ammenofferrc.visibility = View.VISIBLE
//
//                        newshowmore1.setText("Show Less..")
//                        sizeless1 = false
//
//                    }
//                    else{
//                        ammen.visibility = View.GONE
//                        ammenofferrc.visibility = View.GONE
//
//                        newshowmore1.setText("Show More..")
//                        sizeless1 = true
//                    }
//                }
                seemorerc->{
                    if (isLessreview ==true){
                        adapter?.list = ReviewData1()
                        adapter?.notifyDataSetChanged()
                        seemorerc.setText("Read less")
                        isLessreview = false
                    }
                    else{
                        adapter?.list = ReviewData1().take(4)
                        adapter?.notifyDataSetChanged()
                        seemorerc.setText("Read more")
                        isLessreview = true
                    }
                }
            }

        }

    }



}