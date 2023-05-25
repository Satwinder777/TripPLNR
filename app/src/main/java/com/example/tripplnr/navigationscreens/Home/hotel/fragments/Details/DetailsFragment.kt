package com.example.tripplnr.navigationscreens.Home.hotel.fragments.Details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentDetailsBinding
import com.example.tripplnr.databinding.FragmentReviewBinding
import com.example.tripplnr.navigationscreens.Home.dataclass.*
import com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.adapter.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {
    private lateinit var binding : FragmentDetailsBinding

    lateinit var imanirc : RecyclerView
    lateinit var offerrc : RecyclerView
    lateinit var nearbyrc : RecyclerView
    lateinit var ratingrc : RecyclerView

    lateinit var reviewrc : RecyclerView
    lateinit var similarrc : RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
         binding = FragmentDetailsBinding.inflate(layoutInflater)

        return binding.root


    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imanirc = binding.amenitiesRecycler
        GlobalScope.launch {
            val adapter = AmenitiesRecyclerAdapter(imanitiesData())
            imanirc.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        offerrc = binding.amenitiesOfferRecycler
        GlobalScope.launch {
            val adapter = AmenitiesOfferRecyclerAdapter(imanitiesoffer())
            offerrc.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        nearbyrc = binding.ExploreNearByRecycler
        GlobalScope.launch {
            val adapter = NearRecyclerAdapter(nearbyData1())
            nearbyrc.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        ratingrc = binding.ratingRecyclerView
        GlobalScope.launch {
            val adapter = RaingHotelRecyclerAdapter(RatingData1())
            ratingrc.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        reviewrc = binding.reviewRecyclerView
        GlobalScope.launch {
            val adapter = ReviewAdapter(ReviewData1(),requireContext())
            reviewrc.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        similarrc = binding.similarHotelRecyclerview
        GlobalScope.launch {
            val adapter = SimilarHotelAdapter(SimilarData1())
            similarrc.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        onclickreadmore(binding.showmoretxt1)
        onclickreadmore(binding.readmoreTextView)
        onclickreadmore(binding.seeMoreReviews)
    }

    private fun imanitiesData():List<ImanitiesData>{
        var list = listOf<ImanitiesData>(
            ImanitiesData(R.drawable.gym,"Free gym"),
            ImanitiesData(R.drawable.restau,"Room Service"),
            ImanitiesData(R.drawable.bar,"Bar"),
            ImanitiesData(R.drawable.swim,"Swimming Pool"),
            ImanitiesData(R.drawable.park,"Free Parking"),
            ImanitiesData(R.drawable.internet,"Internet"),
            ImanitiesData(R.drawable.time,"24 Hour Available"),
            ImanitiesData(R.drawable.gym,"Gym"),
            ImanitiesData(R.drawable.restau,"Restaurant"),
            ImanitiesData(R.drawable.bar,"Bar"),
            ImanitiesData(R.drawable.swim,"Swimming pool"),
            ImanitiesData(R.drawable.park, "free internet"),
            ImanitiesData(R.drawable.time,"enjoy time"),


            )
        return list
    }
    private fun imanitiesoffer():List<imanitiesOffer>{
        var list = listOf<imanitiesOffer>(
            imanitiesOffer("Room Service"),
            imanitiesOffer("Room Service"),
            imanitiesOffer("Bar"),
            imanitiesOffer("24-hour front desk"),
            imanitiesOffer("terrace"),
            imanitiesOffer("Non-smoking rooms"),
            imanitiesOffer("Family rooms"),
            imanitiesOffer("Air conditioning"),
            imanitiesOffer("Face masks for guests available"),
        )
        return list
    }
    private fun nearbyData1():List<nearbyData>{
        var list = listOf<nearbyData>(

            nearbyData("The north country mall","3.35 Km"),
            nearbyData("Fathe burj","4.65 Km"),
            nearbyData("Mohali cricket stadium","12.5 Km"),
            nearbyData("Panjab university","3.35 Km"),
            nearbyData("Sector 17 market","3.8 Km"),
            nearbyData("The north country mall","1.35 Km"),
            nearbyData("The north country mall","7.77 Km"),
        )
        return list
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

    private fun onclickreadmore(view: View){

        var newshowmore1 = binding.showmoretxt1
        var showmore   = binding.readmoreTextView
        var seeMoreReviews   = binding.seeMoreReviews

        var sizeless :Boolean = true
        var sizeless1 :Boolean = true
        var isLessReview :Boolean = true
        var multitext = binding.editTextTextMultiLine

        var ammen = binding.offeringAmmen
        var ammenofferrc = binding.amenitiesOfferRecycler
        var reviewrc = binding.reviewRecyclerView
        var devider = binding.dividerofferammen

        view.setOnClickListener {

            when(view){
                showmore->{
                    if(sizeless==true){
                        multitext.maxLines = Int.MAX_VALUE

                        showmore.setText("Show Less..")
                        sizeless = false

                    }
                    else{
                        multitext.setLines(4)
                        showmore.setText("Show More..")
                        sizeless = true
                    }
                }
                newshowmore1->{
                    if(sizeless1==true){

                        ammen.visibility = View.VISIBLE
                        ammenofferrc.visibility = View.VISIBLE
                        devider.visibility = View.VISIBLE


                        newshowmore1.setText("Show Less..")
                        sizeless1 = false

                    }
                    else{
                        ammen.visibility = View.GONE
                        ammenofferrc.visibility = View.GONE
                        devider.visibility = View.GONE

                        newshowmore1.setText("Show More..")
                        sizeless1 = true
                    }
                }
            }


        }

    }
}