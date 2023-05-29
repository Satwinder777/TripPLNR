package com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentDealsBinding
import com.example.tripplnr.navigationscreens.Home.dataclass.*
import com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.adapter.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class DealsFragment : Fragment(),bookingCardAdapter.onclickCard {
    private lateinit var binding : FragmentDealsBinding
    lateinit var rc :RecyclerView
    lateinit var imanirc :RecyclerView
    lateinit var offerrc :RecyclerView
    lateinit var nearbyrc :RecyclerView
    lateinit var ratingrc :RecyclerView

    lateinit var reviewrc :RecyclerView
    lateinit var similarrc :RecyclerView
    lateinit var adapter :ReviewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDealsBinding.inflate(layoutInflater)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rc = binding.recommendedDealRecycler
        GlobalScope.launch {
            val adapter = bookingCardAdapter(cardData(),this@DealsFragment)
            rc.adapter = adapter
            adapter.notifyDataSetChanged()
        }

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
            adapter = ReviewAdapter(ReviewData1().take(4),requireContext())
            reviewrc.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        similarrc = binding.similarHotelRecyclerview
        GlobalScope.launch {
            val adapter = SimilarHotelAdapter(SimilarData1())
            similarrc.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        GlobalScope.launch {
            onclickreadmore(binding.readmoreTextView,null)
            onclickreadmore(binding.showmoretxt1,null)
            onclickreadmore(binding.reviewRec,adapter)

        }


    }
    private fun cardData():List<bookingCard>{
        var list = listOf<bookingCard>(
            bookingCard("Deluxe Room","Usd 53"),
            bookingCard("King Room","Usd 54"),
            bookingCard("Grand Room","Usd 12"),
            bookingCard("2bhk Room","Usd 93"),
        )
        return list
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
    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
     fun onclickreadmore(view: View, adapter: ReviewAdapter?){

        var newshowmore1 = binding.showmoretxt1
        var showmore   = binding.readmoreTextView
        var showmoreReview =  binding.reviewRec
        var sizeless :Boolean = true
        var sizeless1 :Boolean = true
        var isLessreview:Boolean = true
        var multitext = binding.editTextTextMultiLine

        var ammen = binding.offeringAmmen
        var ammenofferrc = binding.amenitiesOfferRecycler

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

                        newshowmore1.setText("Show Less..")
                        sizeless1 = false

                    }
                    else{
                        ammen.visibility = View.GONE
                        ammenofferrc.visibility = View.GONE

                        newshowmore1.setText("Show More..")
                        sizeless1 = true
                    }
                }
                 showmoreReview->{
                    if (isLessreview ==true){
                        adapter?.list = ReviewData1()
                        adapter?.notifyDataSetChanged()
                        showmoreReview.setText("Read less")
                        isLessreview = false
                    }
                    else{
                        adapter?.list = ReviewData1().take(4)
                        adapter?.notifyDataSetChanged()
                        showmoreReview.setText("Read more")
                        isLessreview = true
                    }
                }
            }

        }

    }

    override fun cardIsActive(position: Int) {

//        GlobalScope.launch {
            popCard()

//            delay(5000)


//        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse( "https://www.booking.com"))
        requireContext().startActivity(intent)

    }

    @SuppressLint("InflateParams")
    private fun popCard(){
        var view = layoutInflater.inflate(R.layout.loading_page,null,false)

        var pop = PopupWindow(view,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true )
//        pop.contentView = view
        pop.showAtLocation(view,Gravity.CENTER,0,0)
        view.setOnClickListener {
            pop.dismiss()
        }
    }


}